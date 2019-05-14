package com.example.myapplication;

import org.andengine.util.adt.pool.GenericPool;

public class EnemyPool extends GenericPool {

    public static EnemyPool instance;

    public static EnemyPool sharedEnemyPool(){
        if (instance == null) {
            instance = new EnemyPool();
        }
        return instance;
    }

    private EnemyPool(){
        super();
    }

    @Override
    protected Object onAllocatePoolItem() {
        return new Enemy();
    }

    protected void onHandleObtainItem(Enemy pItem) {
        pItem.init();
    }

    /** Called when a projectile is sent to the pool */
    protected void onHandleRecycleItem(final Enemy e) {
        e.sprite.setVisible(false);
        e.sprite.detachSelf();
        e.clean();
    }
}
