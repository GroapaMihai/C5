package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.ColorDao;
import ro.transport.demo.domain.Color;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorDao colorDao;

    @Override
    public Color find(Long id) {
        return colorDao.find(id);
    }

    @Override
    public List<Color> findAll() {
        return colorDao.findAll();
    }
}
