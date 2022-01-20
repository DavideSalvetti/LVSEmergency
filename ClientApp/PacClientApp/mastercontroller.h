#ifndef MASTERCONTROLLER_H
#define MASTERCONTROLLER_H

#include <QObject>
#include <QNetworkAccessManager>

#include "NavigationController.h"
#include "./accountManagementIF/userController.h"
/*!
 * \brief The MasterController class
 * \details Classe principale per l'applicazione.
 */

class MasterController : public QObject
{
    Q_OBJECT
    // rendo accessibili le classi al lato QML
    Q_PROPERTY(NavigationController *ui_navigationController READ getNavigationController CONSTANT)
    Q_PROPERTY(accountmanagementIF::UserController *ui_userController READ getUserController CONSTANT)
public:
    explicit MasterController(QObject *parent = nullptr);

    NavigationController *getNavigationController();
    accountmanagementIF::UserController *getUserController();
signals:

private:
    NavigationController *navigationController {nullptr};
    accountmanagementIF::UserController *userController {nullptr};

    QNetworkAccessManager newtworkManager;

};

#endif // MASTERCONTROLLER_H
