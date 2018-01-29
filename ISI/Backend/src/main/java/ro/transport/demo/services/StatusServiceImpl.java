package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.StatusDao;
import ro.transport.demo.domain.Status;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusDao statusDao;

    @Override
    public Status find(Long id) {
        return statusDao.find(id);
    }

    @Override
    public List<Status> findAll() {
        return statusDao.findAll();
    }

    @Override
    public Status findByName(String name) {
        return statusDao.findByName(name);
    }
}
