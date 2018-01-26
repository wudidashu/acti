package com.zking.test;


import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestActiviti {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    @Test
    public void testCreateTable() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        System.out.println("ok");

    }

    //部署流程
    @Test
    public void deplayProcess() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().name("ask").addClasspathResource("diagrams/askforleave.bpmn").deploy();//流程
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    //启动流程实列
    @Test
    public void startProcessInstance() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println(instance.getProcessDefinitionId());

    }


    //查询angshan
    @Test
    public void getTaskByName() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("zhangshan").list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println(task.getName());

        }

    }


    // 执行任务
    @Test
    public void executeTask() {
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("20002");
    }



    //查询流程状态
// 查询：act_ru_execution
    @Test
    public void queryProcessState() {
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().deploymentId("30001").singleResult();
        if (processInstance == null) {
            System.out.println("流程完毕");
        } else {
            System.out.println("流程运行中..");
        }
    }

    //查询历史
    @Test
    public void queryHisProcessIns() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println(historicActivityInstance.getId());
            System.out.println(historicActivityInstance.getStartTime());//时间
            System.out.println(historicActivityInstance.getDurationInMillis());
        }
    }

    @Test
    public void queryHisTask() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee("wangwu").list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println(historicTaskInstance.getName() + " " + historicTaskInstance.getAssignee());

        }
    }

    //根据id删除
    @Test
    public void deleteProcessDef() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("10001", true);
    }

    @Test
    public void queryProcessStates() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId("30001").singleResult();
        if (processInstance != null) {
            System.out.println("流程正在进行。。");
        } else {
            System.out.println("流程进行完毕。。");
        }
    }

    @Test
    public void setPocessVar(){
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskAssignee("zhangsha").singleResult();
        taskService.setVariable(task.getId(),"请假天数",3);
    }

}
