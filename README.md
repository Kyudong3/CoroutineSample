# Coroutine Practice

### Starting Coroutine

- **Process(프로세스)** : Instance of running Application.
  It starts when application starts. Process saves the status. 
  Application can consist multiple process

- **Thread(스레드)** : Include a series of commands that process will run. 
  So process include at least one thread. 
  This thread create for entry point of application. 
  Basically, entry point of application is main() function

- **Coroutine(코루틴)** : Light-weight thread. Coroutine run inside thread.
  Thread can have many coroutine inside but only one command(coroutine) can be run at given time



### Difference ( Coroutine & Thread )

1. **Speed** (Coroutine is faster)
2. **cost** (Coroutine has lower cost)



### Concurrency(동시성)

- Concurrency is Logical word. In concurrency, jobs seems to run at the same time. Concurrency is method to operate multi-thread in single core. It can be also used in multi core.
- Multiple threads are running alternately for multi-tasking.
- MultiTasking in single core using concurrency seems to run parallel, but actually, it's running alternately little by little

### Parallel(병렬)

- Parallel is physical word. In parallel, jobs are actually processed at the same time. Parallel is method to run multi-thread in multi core.
- Each core that contains multiple thread runs at the same time

![Concurrency](/Users/kyudong/Desktop/Concurrency.jpg)

(출처: [concurrency](https://www.codeproject.com/Articles/1267757/Concurrency-vs-Parallelism))



### why concurreny is difficult?

1. **Race Conditions** (경합 조건)
   - Occurs when writing the code concurrently but, expecting code to operate sequentially. 

2. **Atomic operations** (원자성 위반)
   - Data used by jobs can be accessed without interference

3. **DeadLock** (교착 상태)
   - To synchronize the code in concurrency, we need to pause or block thread while other thread's job is finished
   - Complexity of this situation, circular dependencies(순환적 의존성) occurs





### Concurrency in Kotlin

- **Non-blocking** 

  - Thread is heavy and needs high cost. If thread is blocked, resource are wasted. So, kotlin provides **suspendable computations(중단 가능 연산)**. This function doesn't block running thread, just stopping.
  - kotlin provides primitives (channels, actors, mutual exclusions ...) 

- **Suspendable computations** 

  - Pause thread running, not blocking
  - suspendable functions use **suspend** indentifier
  - Basically, suspendable computations run sequentially

- **Readability**

- **Flexibililty**

- **Lambda suspendable**

  - Anonymous local function. It's same as general lambda but, pause itself by calling other suspendable function 

- **Coroutine Dispatcher**

  - Dispatcher is used when to decide which coroutine should be started or resumed
  - All coroutines dispatchers need to implement CoroutineDispatcher interface

- **Coroutine Builder**

  - Creating coroutines that gets lambda suspendable and run
  - **Async( )** : used for starting coroutines that expect result. This function captures all exceptions and put in result. Returns Deferred<T>
  - **launch( )** : start coroutines that doesn't return result. Returns Job
  - **runBlocking( )** : Block current thread while coroutine terminates.

  
