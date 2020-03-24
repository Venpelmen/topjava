package ru.javawebinar.topjava.service;

import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class ExternalResourceWithTimeCalc {

    static Logger logger = LoggerFactory.getLogger(MealServiceTest.class);

    static List<TestResult> resultSet = new ArrayList<>();


    static ExternalResource externalResourceForClass = new ExternalResource() {

        @Override
        protected void after() {
            resultSet.forEach(item -> logger.info(item.toString()));
            super.after();
        }

        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    long startMills = System.currentTimeMillis();
                    base.evaluate();
                    long workingTime = System.currentTimeMillis() - startMills;
                    TestResult testResult = new TestResult(description.getMethodName(), workingTime);
                    logger.info(testResult.toString());
                    resultSet.add(testResult);
                }
            };
        }
    };


    ExternalResource externalResourceForMethod = new ExternalResource() {
        @Override
        protected void after() {
            resultSet.forEach(item -> logger.info(item.toString()));
            super.after();
        }

        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    long startMills = System.currentTimeMillis();
                    base.evaluate();
                    long workingTime = System.currentTimeMillis() - startMills;
                    TestResult testResult = new TestResult(description.getMethodName(), workingTime);
                    logger.info(testResult.toString());
                    resultSet.add(testResult);
                }
            };
        }
    };
}
