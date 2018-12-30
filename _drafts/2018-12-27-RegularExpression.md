---
title: "Usage of Regular Expression"
date: 2018-12-20 12:18:32 +0800
tags: Regular Expression
key: 2018-12-20-RegularExpression
---

# Overview
Regular Expression is good in many cases like log analysis, and quick searching etc. However, 9 out of 10 Regular Expressions are not correct when they are used for specific business purpose, such as the email address validation. 



Search Regular Expression, following is the first 
https://www.quora.com/Why-are-regular-expressions-discouraged-from-production-code-that-is-worked-on-by-a-team


Chomsky hierarchy
https://nikic.github.io/2012/06/15/The-true-power-of-regular-expressions.html

Regular Expression limitations
https://blogs.msdn.microsoft.com/jaredpar/2008/10/15/regular-expression-limitations/

IP Address Regular Expression.

Longest Regular Expression

Why it's named Regular Expression, not Matching Expression, String Expression.
Regular Language Regular Expression https://www.cs.odu.edu/~toida/nerzic/390teched/regular/reg-lang/definitions.html

Kleene's Theorem
http://www.cs.cornell.edu/courses/cs2800/2017sp/lectures/lec27-kleene.html

Kleene's theorem: The set of regular languages, the set of NFA-recognizable languages, and the set of DFA-recognizable languages are all the same


Automata Theory
https://en.wikipedia.org/wiki/Automata_theory (Can see Turing Machine, Pushdown automaton figure)
https://cs.stanford.edu/people/eroberts/courses/soco/projects/2004-05/automata-theory/basics.html


Is it a state machine when it works with memory?
https://stackoverflow.com/questions/43583929/is-it-a-state-machine-when-it-works-with-memory


21 commas, not memory, but states.
https://stackoverflow.com/questions/42006575/regular-expression-to-count-number-of-pattern-in-a-string

https://www.regular-expressions.info/balancing.html

https://www.rexegg.com/regex-recursion.html

Email Validator
http://sphinx.mythic-beasts.com/~pdw/cgi-bin/emailvalidate

.Net Regular Expression
https://docs.microsoft.com/en-us/dotnet/standard/base-types/regular-expressions

Regular Expression is a good way to spend your Saturday afternoon in trial and error. In the college, I implemented a very naive editor with Java highlight. I used one Saturday afternoon to create the Regular Expressions to highlight comments. 


![JPad, a naive editor with Java highlight](/assets/JPad.png)

It's worthy to emphasize the usage of exceptions.

<!--more-->

# Kinds of Exception

[Exceptions in Java Language Specification] describes kinds of exceptions. We can see the figure to see the kinds of Exception. 


1. `Exception` is the superclass of all the exceptions from which ordinary programs may `wish to recover`.
2. `Error` is the superclass of all the exceptions from which ordinary programs are `not ordinarily expected to recover`.
3. `Checked Exception` must be handled, while `Unchecked Exception` is not.

For example, when we invoke a method which may throw `Checked Exception`, we must surround with `try/catch` or add throw declaration. 

{% highlight JAVA %}
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  // must do something here
}
{% endhighlight %}

{% highlight JAVA %}
public void invoker() throws CheckedException {
  // some routines which may throw CheckedException
}
{% endhighlight %}

# Exception 101
## DON'T Swallow Exception

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

`DON'T Swallow Exception!`

This is `extremely important`. When the system goes online, finding swallowed exception is harder than looking for a needle in the haystack. 

If you are not sure how to handle the exception, re-throw it. 

## Write Informative and Insensitive Exception Message, and Keep the Cause

{% highlight JAVA %}
// Bad Case
try {
  // some routines which may throw IOException
} catch (IOException ex) {
  throw new MyException("Unknown issue.");
}
{% endhighlight %}

{% highlight JAVA %}
// Bad Case, user object might include sensitive information.
try {
  // some routines which may throw UserNotFoundException
} catch (UserNotFoundException ex) {
  throw new MyException("Can not find the user: " + user.toString(), ex);
}
{% endhighlight %}

{% highlight JAVA %}
// Good Case
try {
  // some routines which may throw IOException
} catch (IOException ex) {
  throw new MyException("Informative message here.", ex);
}
{% endhighlight %}

## Throw/Catch Specific Exception Instead of Its Superclass

**Don't Throw/Catch `Exception` and `Throwable`.**

 {% highlight JAVA %}
// Bad Case
public void useSpecificException() throws Exception {
  // some routines which may throw IOException and TimeoutException ;
}
{% endhighlight %}

 {% highlight JAVA %}
// Good Case
public void useSpecificException() throws IOException, TimeoutException { 
  // some routines which may throw IOException and TimeoutException ;
}
{% endhighlight %}

 {% highlight JAVA %}
// Bad Case, as the Exception might be RuntimeException, which should have other ways to handle.
public void useSpecificTryCatch() { 
  try {
    // some routines which may throw IOException and TimeoutException ;
  } catch (Exception ex) {
    // Do something here for Exception;
  } 
}
{% endhighlight %}

 {% highlight JAVA %}
// Good Case. This might be controversial. In real cases, developers just log each exception, therefore 
//     `try {} catch (IOException | TimeoutException ex) {}`
// might be used.
public void useSpecificTryCatch() { 
  try {
    // some routines which may throw IOException and TimeoutException ;
  } catch (IOException ex) {
    // Do something here for IOException;
  } catch (TimeoutException ex) {
    // Do something else here for TimeoutException;
  }
}
{% endhighlight %}

## Log Only When Exception is Handled
{% highlight JAVA %}
// Bad Case, because same exception might be logged many times (log here and the outer invokers),
// which messes up the log and monitoring tool.
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  LOGGER.error("Informative message in log", ex);
  throw ex;
}
{% endhighlight %}

{% highlight JAVA %}
// Good Case
try {
  // some routines which may throw CheckedException
} catch (CheckedException ex) {
  LOGGER.error("Informative message in log", ex);
  // Do something to recover if needed. 
}
{% endhighlight %}

## Release Resources Finally

From Java 7, we should use `try-with-resources` for `InputStream` etc. 
{% highlight JAVA %}
try (InputStream is = new FileInputStream("file")) {
  is.read();
}
{% endhighlight %}

Prior to Java 7, or for those resources which do not implement `java.lang.AutoCloseable`, remember to release resources in `finally` block. [IOUtils.closeQuietly in Apache IOUtils] is used by many developers prior to Java 7, but it swallows `IOException` which might be harmful in many cases especially closing a writer. It's also discussed in `Google guava`, [95% of all usage of Closeable.closeQuietly is broken].

{% highlight JAVA %}
InputStream is = null;
try {
  is = new FileInputStream("file");
  is.read();
} finally {
  if (is != null) {
    is.close();
  }
}
{% endhighlight %}

# Next
Some notorious exceptions (NullPointerException, OutOfMemoryError etc.) and corresponding mitigation will be described.

# Reference

\[1\] [Swallowed Exceptions: The Silent Killer of Java Applications] 

\[2\] [Exceptions in Java Language Specification]

\[3\] [The try-with-resources Statement]

\[4\] [IOUtils.closeQuietly in Apache IOUtils]

\[5\] [95% of all usage of Closeable.closeQuietly is broken]

[Swallowed Exceptions: The Silent Killer of Java Applications]:https://blog.takipi.com/swallowed-exceptions-the-silent-killer-of-java-applications/

[Exceptions in Java Language Specification]:https://docs.oracle.com/javase/specs/jls/se7/html/jls-11.html

[The try-with-resources Statement]:https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

[IOUtils.closeQuietly in Apache IOUtils]:https://github.com/apache/commons-io/blob/58b0f795b31482daa6bb5473a8b2c398e029f5fb/src/main/java/org/apache/commons/io/IOUtils.java#L359

[95% of all usage of Closeable.closeQuietly is broken]:https://github.com/google/guava/issues/1118