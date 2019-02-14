package net.ukr.just_void;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class FileEntityService {
    @Autowired
    private FileEntityDAO fileEntityDAO;

    @Transactional
    public void addFileEntity(FileEntity fileEntity) {
        fileEntityDAO.save(fileEntity);
    }

    @Transactional
    public List<FileEntity> findAllFileEntities() {
        return fileEntityDAO.findAll();
    }

    @Transactional
    public FileEntity getByHashId(int hashId) {
        return fileEntityDAO.getFileEntityByHashEquals(hashId);
    }

    @Transactional
    public boolean isEmpty() {
        return fileEntityDAO.count() == 0;
    }

    @Transactional
    public Set<Integer> getFileEntityHashSet() {
        return fileEntityDAO.getFileEntityDTO();
    }

    @Transactional
    public boolean deleteByHashId(int hashId) {
        boolean exists = (fileEntityDAO.existsFileEntityByHashId(hashId));
        if (exists) {
            fileEntityDAO.removeFileEntityByHashEquals(hashId);
        }
        return exists;
    }

}
