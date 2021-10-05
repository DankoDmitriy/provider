package com.danko.provider.Main.TMP;

public class TMP_NOT_USED_UserActionService {
    //    List<UserAction> findAll() throws ServiceException;

//    Optional<UserAction> findById(Long id) throws ServiceException;

//    List<UserAction> findAllByUserId(long userId) throws ServiceException;

    //    long add(UserAction userAction, long userId, long tariffId) throws ServiceException;


    //    @Override
//    public List<UserAction> findAll() throws ServiceException {
//        try {
//            try {
//                transactionManager.startTransaction();
//                return userActionDao.findAll();
//            } catch (DaoException e) {
//                throw new ServiceException(e);
//            } finally {
//                transactionManager.endTransaction();
//            }
//        } catch (DaoException | ServiceException e1) {
//            throw new ServiceException(e1);
//        }
//    }

//    @Override
//    public Optional<UserAction> findById(Long id) throws ServiceException {
//        try {
//            try {
//                transactionManager.startTransaction();
//                return userActionDao.findById(id);
//            } catch (DaoException e) {
//                throw new ServiceException(e);
//            } finally {
//                transactionManager.endTransaction();
//            }
//        } catch (DaoException | ServiceException e1) {
//            throw new ServiceException(e1);
//        }
//    }

//    @Override
//    public List<UserAction> findAllByUserId(long userId) throws ServiceException {
//        try {
//            try {
//                transactionManager.startTransaction();
//                return userActionDao.findAllByUserId(userId);
//            } catch (DaoException e) {
//                throw new ServiceException(e);
//            } finally {
//                transactionManager.endTransaction();
//            }
//        } catch (DaoException | ServiceException e) {
//            throw new ServiceException(e);
//        }
//    }

    //    @Override
//    public long add(UserAction userAction, long userId, long tariffId) throws ServiceException {
//        try {
//            try {
//                transactionManager.startTransaction();
//                long generatedId = userActionDao.add(userAction, userId, tariffId);
//                transactionManager.commit();
//                return generatedId;
//            } catch (DaoException e) {
//                transactionManager.rollback();
//                throw new ServiceException(e);
//            } finally {
//                transactionManager.endTransaction();
//            }
//        } catch (DaoException | ServiceException e1) {
//            throw new ServiceException(e1);
//        }
//    }

}
