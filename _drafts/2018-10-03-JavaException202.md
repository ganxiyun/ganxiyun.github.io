---
title: "Java Exception 202"
date: 2019-03-30 16:18:32 +0800
tags: Java Exception
key: 2018-10-03-JavaException202
---

# Overview


<!--more-->

# Suppressed Exception

# Exception in Runnable

# InterruptedException
The first time I wrote Java thread, I only knew `Thread.sleep` might throw `InterruptedException`. At that time, I didn't care about `InterruptedException` at all.
```Java
public Thread thread = new Thread(new Runnable() {
  public void run() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

});
```

# OutOfMemoryError
EXITONOOM

# StackOverflowError

# Swallowed Exception