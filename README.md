## Start two nodes of application:
- First - start main class _akka.executor.service.Service_ with program arguments **2551 master**
- Second - start main class _akka.executor.service.Service_ with program arguments **2552 slave**

Идея в том, чтобы мы запустили два сервиса, и один из них(master) посылал на исполнение таски,
а эти таски исполнянись уже двумя сервисами(master и slave) при помощи одноименных экторов

На данный момент запускаются два сервиса, но исполняет таски только один из них(master), второй подключить не удалось 