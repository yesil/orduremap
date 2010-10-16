package com.treflex.orduremap;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.dev.LocalTaskQueue;
import com.google.appengine.api.labs.taskqueue.dev.QueueStateInfo;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;

public class TaskQueueTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	// Run this test twice to demonstrate we're not leaking state across tests.
	// If we _are_ leaking state across tests we'll get an exception on the
	// second test because there will already be a task with the given name.
	private void doTest() throws InterruptedException {
		QueueFactory.getDefaultQueue().add(TaskOptions.Builder.taskName("task29"));
		// give the task time to execute if tasks are actually enabled (which
		// they
		// aren't, but that's part of the test)
		Thread.sleep(1000);
		LocalTaskQueue ltq = LocalTaskQueueTestConfig.getLocalTaskQueue();
		QueueStateInfo qsi = ltq.getQueueStateInfo().get(QueueFactory.getDefaultQueue().getQueueName());
		assertEquals(1, qsi.getTaskInfo().size());
		assertEquals("task29", qsi.getTaskInfo().get(0).getTaskName());
	}

	@Test
	public void testTaskGetsScheduled1() throws InterruptedException {
		doTest();
	}

	@Test
	public void testTaskGetsScheduled2() throws InterruptedException {
		doTest();
	}
}