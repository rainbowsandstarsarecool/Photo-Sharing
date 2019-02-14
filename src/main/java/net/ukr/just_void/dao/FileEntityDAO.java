package net.ukr.just_void.dao;

import net.ukr.just_void.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FileEntityDAO extends JpaRepository<FileEntity, Integer> {
    @Query("SELECT CASE WHEN (count(f)>0) THEN true ELSE false END FROM FileEntity f WHERE f.hash = :hash")
    boolean existsFileEntityByHashId(@Param("hash") int hashId);

    @Query("SELECT f.hash FROM FileEntity f")
    Set<Integer> getFileEntityDTO();

    FileEntity getFileEntityByHashEquals(int hashId);

    void removeFileEntityByHashEquals(int hashId);
}
