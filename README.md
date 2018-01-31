## Start two nodes of application:
- First - start producer class _akka.executor.service.Producer_ with program argument **2551** 
- Second - start consumer class _akka.executor.service.Consumer_ with program argument **2553**

**На данный момент запускается producer и множество consumer-ов:**
- producer отправляет сообщения в mailbox, 
- сообщения распределяются среди consumer экторами при помощи router dispatcher, который работает в режиме adaptive load balancing strategy

https://lostintimedev.com/2017/05/26/running-akka-cluster-on-docker-swarm.html


## Problem with Netty port already in use:
https://github.com/mrniko/netty-socketio/issues/254
https://github.com/akkadotnet/akka.net/issues/2477
https://stackoverflow.com/questions/14388706/socket-options-so-reuseaddr-and-so-reuseport-how-do-they-differ-do-they-mean-t
