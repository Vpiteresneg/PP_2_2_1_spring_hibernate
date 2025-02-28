package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    //TypedQuery<User> — это типизированный запрос в контексте Java Persistence API (JPA) или Hibernate, который исполь-
    // зуется для выполнения запросов, возвращающих данные в виде объекта определённого типа, в данном случае — User.
    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        // Создаём запрос с использованием Hibernate (или JPA), используя текущую сессию
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User u where u.car.model = :model and u.car.series = :series", User.class);
                query.setParameter("model", model);// Устанавливаем параметры запроса
                query.setParameter("series", series);// Устанавливаем параметры запроса

        // Выполняем запрос, получаем список пользователей и возвращаем первого пользователя, если такой есть
        return query.getResultList().stream().findFirst().orElse(null);
    }

}
