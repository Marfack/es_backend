package com.es.service;

import com.es.vo.StaffRegisterVo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.Future;

/**
 * Created on 2021/12/18 15:38
 * 查询及显示职业信息，根据职业筛选员工信息等服务
 * @author Marfack
 */
public interface CareerService {

    /**
     * 初始化主页信息，用于传输所有职业大类的列表数据
     * @return 异步任务返回包含全部职业大类信息的Json串
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getCareerClasses() throws JsonProcessingException;

    /**
     * 用于返回用户查询的某一大类的所有职业信息
     * @param classId 职业大类id
     * @return 异步任务返回包含全部职业信息的Json串
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getCareerList(int classId) throws JsonProcessingException;

    /**
     * 用于返回用户查询的某职业的所有员工信息
     * @param careerId 职业id
     * @param page 分页页数，每页20条信息
     * @return 异步返回包含全部员工信息的Json串
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getStaffList(int careerId, long page) throws JsonProcessingException;

    /**
     * 用于返回用户选择员工的详细信息
     * @param staffId 用户选择的员工id
     * @return 异步返回包含员工信息的Json串
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getStaffDetails(long staffId) throws JsonProcessingException;

    /**
     * 用户查看所有员工列表
     * @param userId 用户id
     * @return 用户拥有的员工列表
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getStaffs(long userId) throws JsonProcessingException;

    /**
     * 更新员工信息
     * @param vo 员工信息接收类
     * @param userId 用户id
     * @return 是否更新成功
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> updateStaff(StaffRegisterVo vo, long userId) throws JsonProcessingException;

    /**
     * 删除员工
     * @param staffId 需要删除员工的id
     * @return 是否删除成功
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> removeStaff(long staffId) throws JsonProcessingException;

    /**
     * 根据城市查询员工
     * @param page 页数
     * @param careerId 职业id
     * @param cityId 城市id
     * @return 员工详情列表
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getStaffList(int careerId, int cityId, long page) throws JsonProcessingException;

    /**
     * 根据用户提交的约束条件，按照位置、职业、评分来推荐一批员工
     * @param classId 职业方向id
     * @param cityId 城市id
     * @return 返回推荐的员工列表
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> recommend(int classId, int cityId) throws JsonProcessingException;
}
