package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.PriorityDao;
import ro.transport.demo.domain.Priority;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {
    @Autowired
    private PriorityDao priorityDao;

    @Override
    public Priority find(Long id) {
        return priorityDao.find(id);
    }

    @Override
    public List<Priority> findAll() {
        return priorityDao.findAll();
    }

    @Override
    public Priority findByName(String name) {
        return priorityDao.findByName(name);
    }
}
