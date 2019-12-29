package com.example.vancouvertourismappfinal.RestRoomFinderClasses.database.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bathroomEntityDaoConfig;

    private final BathroomEntityDao bathroomEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bathroomEntityDaoConfig = daoConfigMap.get(BathroomEntityDao.class).clone();
        bathroomEntityDaoConfig.initIdentityScope(type);

        bathroomEntityDao = new BathroomEntityDao(bathroomEntityDaoConfig, this);

        registerDao(BathroomEntity.class, bathroomEntityDao);
    }
    
    public void clear() {
        bathroomEntityDaoConfig.getIdentityScope().clear();
    }

    public BathroomEntityDao getBathroomEntityDao() {
        return bathroomEntityDao;
    }

}
