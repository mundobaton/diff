package com.waes.diff.repositories;

import com.waes.diff.core.entities.Content;
import com.waes.diff.core.repositories.FindContentRepository;
import com.waes.diff.core.repositories.RepositoryException;
import com.waes.diff.core.repositories.SaveContentRepository;
import com.waes.diff.core.repositories.UpdateContentRepository;
import org.apache.commons.dbutils.QueryRunner;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Singleton
public class DefaultContentRepository extends BaseRepository implements FindContentRepository, SaveContentRepository, UpdateContentRepository {

    @Inject
    public DefaultContentRepository(QueryRunner queryRunner) {
        super(queryRunner);
    }

    @Override
    public Optional<Content> find(Long contentId) {
        Content content;
        String query = "SELECT id, left_content, right_content, result FROM contents WHERE content_id = ?";
        try {
            content = select(query, (ResultSet rs) -> {
                Content result = null;
                if (rs.first()) {
                    result = new Content();
                    result.setId(rs.getLong("id"));
                    result.setContentId(contentId);
                    result.setLeftContent(rs.getString("left_content"));
                    result.setRightContent(rs.getString("right_content"));
                    result.setResult(rs.getString("result"));
                }
                return result;
            }, contentId);

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(content);
    }

    @Override
    public void save(com.waes.diff.core.model.Content content) {
        String query = "INSERT INTO contents(content_id, left_content, right_content, result) VALUES(?,?,?,?)";
        try {
            insert(query, (ResultSet rs) -> null, content.getContentId(), content.getLeftContent(), content.getRightContent(), content.getResult());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(com.waes.diff.core.model.Content content) {
        String query = "UPDATE contents SET left_content = ?, right_content = ?, result = ? WHERE content_id = ?";
        try {
            update(query, content.getLeftContent(), content.getRightContent(), content.getResult(), content.getContentId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
