package com.tecforte.blog.repository;
import com.tecforte.blog.domain.Blog;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Blog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("select blog from Blog blog where blog.user.login = ?#{principal.username}")
    List<Blog> findByUserIsCurrentUser();

    @Query("select distinct blog from Blog blog left join fetch blog.entries")
    List<Blog> findAllWithEagerRelationships();

    // Clean Blog's Entry
    @Modifying
    @Query("delete from Entry entry where entry.id = :id AND entry.blog.id = :blog")
    void cleanEntry(@Param("id") Long id, @Param("blog") Long blog);
}
