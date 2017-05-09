package com.chanzor.office.common;

public class Constants {

	//机构类型
	public static final String ORG_TYPE_COMPANY = "1";
	public static final String ORG_TYPE_DEPTMENT = "2";
	public static final String ORG_TYPE_GROUP = "3";
	public static final String ORG_TYPE_OTHER = "4";
	
	//机构等级
	public static final String ORG_LEVEL_ONE = "1";
	public static final String ORG_LEVEL_TWO = "2";
	public static final String ORG_LEVEL_THREE = "3";
	public static final String ORG_LEVEL_FOUR = "4";
	
	public static final String ORG_FINANCE_DEPTNAME = "财务部";
	public static final String ORG_OPERATION_DEPTNAME = "运营部";
	public static final String ORG_MARKETING_CENTER_DEPTNAME = "营销中心";
	public static final String ORG_SALE_DEPTNAME = "销售部";
	public static final String ORG_TRENCH_DEPTNAME = "渠道部";

	
	
	//角色等级，用于工作流标识身份，roleLevel
	public static final Integer GROUP_EMPLOYEE_ROLELEVEL = 0;  //组员工
	public static final Integer COMMON_EMPLOYEE_ROLELEVEL = 1;  //普通员工
	public static final Integer DEPT_MANAGER_ROLELEVEL = 2;     //部门经理
	public static final Integer VICE_PRESIDENT_ROLELEVEL = 3;     //副总经理
	public static final Integer PRESIDENT_ROLELEVEL = 4;     //总经理
	public static final Integer DEPT_CHARGE_ROLELEVEL  = 5;     //部门主管
	
	public static final Integer FINANCE_MANAGER_ROLELEVEL   = 7;     //财务经理
	public static final Integer FINANCE_OPERATOR_ROLELEVEL  = 8;     //财务经办
	public static final Integer FINANCE_CHECKER_ROLELEVEL  = 9;     //财务审核
	
}
