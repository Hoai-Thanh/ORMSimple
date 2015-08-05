package com.sample.orm.ormsample.dao;

import java.util.List;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public interface IDataDao<Model, Id> {
    public boolean isExist(Id id);
    public boolean insert(Model model);
    public boolean update(Model model);
    public Model getById(Id id);
    public List<Model> getAll();
    public boolean deleteById(Id id);
    public boolean deleteAll();
}
