package com.waes.diff.core.usecases.impl;

import com.waes.diff.core.usecases.DecodeContent;
import com.waes.diff.core.usecases.ProcessDiff;
import com.waes.diff.core.usecases.UpdateContent;
import com.waes.diff.core.usecases.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DefaultProcessDiff implements ProcessDiff {

    private UpdateContent updateContent;
    private DecodeContent decodeContent;

    @Inject
    public DefaultProcessDiff(UpdateContent updateContent, DecodeContent decodeContent) {
        this.updateContent = updateContent;
        this.decodeContent = decodeContent;
    }

    @Override
    public Void apply(Model model) {
        try {
            validateModel(model);
        } catch (IllegalArgumentException e) {
            log.error("process diff error: " + e.getMessage());
            throw new UseCaseException(e.getMessage());
        }

        String leftContent = decodeContent.apply(DecodeContent.Model.builder().content(model.getContent().getLeftContent()).build());
        log.debug("left content: " + DecodeContent.Model.builder().content(model.getContent().getLeftContent()) + "decoded to: " + leftContent);
        String rightContent = decodeContent.apply(DecodeContent.Model.builder().content(model.getContent().getRightContent()).build());
        log.debug("right content: " + DecodeContent.Model.builder().content(model.getContent().getRightContent()) + "decoded to: " + rightContent);
        model.getContent().setResult(doDiff(leftContent, rightContent));

        updateContent.apply(UpdateContent.Model.builder().content(model.getContent()).build());
        log.debug("diff result updated with id: " + model.getContent().getContentId());
        return null;
    }

    private static void validateModel(Model model) {
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("Model is required");
        }

        if (Objects.isNull(model.getContent())) {
            throw new IllegalArgumentException("Content is required");
        }

        if (StringUtils.isBlank(model.getContent().getLeftContent()) || StringUtils.isBlank(model.getContent().getRightContent())) {
            throw new IllegalArgumentException("Left and Right contents are required");
        }
    }

    /**
     * Diffs between 2 contents, if both contents are equal we just return it.
     * If both contents have different lengths, we just inform it.
     * If both content have same length, then we found the differences among them looking for the offsets and the diff lengths.
     * @param leftContent
     * @param rightContent
     * @return
     */
    String doDiff(String leftContent, String rightContent) {
        if (leftContent.equals(rightContent)) {
            return "left and right contents are equal";
        }

        if (leftContent.length() != rightContent.length()) {
            return "left and right contents have different lengths";
        }

        return new DiffedContent(leftContent, rightContent).getResult();
    }

    private class DiffedContent {
        private List<DiffUnit> diffUnits;

        DiffedContent(String leftContent, String rightContent) {
            this.diffUnits = new ArrayList<>();
            performDiff(leftContent, rightContent);
        }

        private void performDiff(String leftContent, String rightContent) {
            int length = 0;
            boolean foundDiff = false;
            int idx;

            for (idx = 0; idx < leftContent.length(); idx++) {
                char leftChar = leftContent.charAt(idx);
                char rightChar = rightContent.charAt(idx);

                if (leftChar != rightChar) {
                    foundDiff = true;
                    length++;
                } else {
                    if (foundDiff) {
                        diffUnits.add(new DiffUnit(idx, length));
                        length = 0;
                        foundDiff = false;
                    }
                }
            }
            //this gets the case the last character is an actual difference
            if (foundDiff) {
                diffUnits.add(new DiffUnit(idx, length));
            }
        }

        public String getResult() {
            StringBuilder sb = new StringBuilder();
            sb.append(diffUnits.size() + " differences found: ");
            for (int i = 0; i < diffUnits.size(); i++) {
                DiffUnit du = diffUnits.get(i);
                sb.append("pos: " + du.offset + " length: " + du.length);
                if (i + 1 != diffUnits.size()) {
                    sb.append(", ");
                }
            }

            return sb.toString();
        }

        private class DiffUnit {

            private int offset;
            private int length;

            public DiffUnit(int offset, int length) {
                this.offset = offset;
                this.length = length;
            }
        }
    }
}
