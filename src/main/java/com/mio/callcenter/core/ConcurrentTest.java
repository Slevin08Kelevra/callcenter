package com.mio.callcenter.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mio.callcenter.entities.Director;
import com.mio.callcenter.entities.Employee;
import com.mio.callcenter.entities.Operator;
import com.mio.callcenter.entities.Supervisor;
import com.mio.callcenter.util.TimeOut;

public class ConcurrentTest {
	
	public int poolSize = 20;
	public int loopCount = 10;
	public Object lock = new Object();//volatile??
	public ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
	
	public static void main(String[] args) {
		
		ConcurrentTest ct = new ConcurrentTest();
		
		String operators = "Jesus,Joseph,Aline,Peter,Steve,Vauhn,Jon,Silvia,Ruperto";
		List<Employee> attenders = Arrays.stream(operators.split(",")).map(String::trim).map(a -> new Operator(a))
				.collect(Collectors.toList());

		attenders.add(new Supervisor("Slevin"));
		attenders.add(new Supervisor("Andy"));
		attenders.add(new Director("Alfred"));
		
		Tasks task = new Tasks();
		CallCenter cc = new CallCenter(attenders, 50);
		TimeOut to = new TimeOut(4000, 5000);
		
		IntStream.rangeClosed(1, ct.poolSize).forEach(a -> ct.threadPool.execute(task.concurrentTest(ct.lock, ct.loopCount, cc)));
		
		to.setRandomTimeOut();
		System.out.println("Call size: "+ (ct.loopCount * ct.poolSize));
		System.out.println("Starting...");
		
		synchronized (ct.lock) {
			ct.lock.notifyAll();
		}
		
		to.setRandomTimeOut(3);
		
		cc.initShutDownProcess();
		ct.threadPool.shutdown();
		
	}
	
	

}
