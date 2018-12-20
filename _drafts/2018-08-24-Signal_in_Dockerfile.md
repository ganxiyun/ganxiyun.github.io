---
title: "How to Write Dockerfile to Support Graceful Shutdown"
date: 2018-08-15 16:18:32 +0800
tags: Report Progress Objective
key: 2018-08-15-WeeklyReport
---

exec


docker run image_name /bin/bash -c "mvn" can propagate SIGTERM signal

vs.

docker run image_name /bin/bash -c "echo abc;mvn" it will not be propagated to mvn

docker run image_name /bin/bash -c "echo abc;exec mvn"  can solve this issue.

docker run image_name /bin/bash -c "echo abc&&mvn" can propagate SIGTERM signal

fork exec ?


trap : tldp.org