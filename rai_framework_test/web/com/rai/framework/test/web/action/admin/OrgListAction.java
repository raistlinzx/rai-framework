package com.rai.framework.test.web.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rai.framework.model.common.ConditionModel;
import com.rai.framework.model.common.QueryModel;
import com.rai.framework.test.model.Organization;
import com.rai.framework.utils.PageControl;
import com.rai.framework.web.struts.action.common.GeneralAction;

/**
 * QueryModel queryModel = new QueryModel(Organization.class); 指明查询组织机构对象 <br>
 * ConditionModel.eq("parentOrg.id", parentId)即是描述一个条件<br>
 * 通过queryModel.add(ConditionModel.eq("parentOrg.id", parentId))添加进去,可以添加多个条件<br>
 * <br>
 * NOTE：ConditionModel.eq(KEY,VALUE)中 的KEY代表model中类属性名称，支持通过“.”向下延展<br>
 * 比如父单位的id就是parentOrg.id<br>
 * <br>
 * NOTE：ConditionModel.isNull("[parentOrg.id]")，这个条件中用到了“[”和“]”。<br>
 * 这是一种特殊用法，因为一般情况下，parentOrg.id这种对象引用关系，会在生成SQL时自动连表查询<br>
 * 有些时候，我们不想他连表，则使用“[parentOrg.id]”这种写法。举例：<br>
 * 
 * <pre>
 * 	Book中有一个对象 writer(作家)，如果查询作家ID=1的图书。用法如下:
 * 	QueryModel queryModel=new QueryModel(Book.class);
 * 	queryModel.add(ConditionModel.eq("writer.id",1); 
 * 	则生成SQL如下 select b.* from book b,writer w where b.write_id=w.id and w.id=1;
 * 	
 * 	如果使用queryModel.add(ConditionModel.eq("[writer.id]",1);
 * 	则生成SQL如下 select b.* from book b where b.write_id=1;
 * 
 * 	更复杂的情况比如:
 * 	Book中有一个对象 writer(作家)，writer有所属单位org，如果查询作家单位ID=100的图书。用法如下:
 * 	QueryModel queryModel=new QueryModel(Book.class);
 * 	queryModel.add(ConditionModel.eq("writer.org.id",100);
 * 	则生成SQL如下 select b.* from book b,writer w,org o, where b.write_id=w.id and o.id=w.org_id and o.id=100;
 * 	
 * 	如果使用queryModel.add(ConditionModel.eq("writer.[org.id]",100);
 * 	则生成SQL如下 select b.* from book b,writer w where b.write_id=w.id and w.org_id=100;
 * 
 * </pre>
 */
@Controller
public class OrgListAction extends GeneralAction {

	@RequestMapping(value = "/admin/orgList")
	public String actionExecute(HttpServletRequest request, Model model)
			throws Exception {

		String parentId = request.getParameter("parentId");

		// 生成查询条件queryModel
		QueryModel queryModel = new QueryModel(Organization.class);
		if (StringUtils.isNotBlank(parentId))
			queryModel.add(ConditionModel.eq("[parentOrg.id]", parentId));
		else
			queryModel.add(ConditionModel.isNull("[parentOrg.id]"));
		// queryModel.addOrder("idx", "ASC");

		PageControl pageControl = this.createPageControl(request);
		this.generalManager.find(queryModel, pageControl);

		model.addAttribute("pageControl", pageControl);

		if (StringUtils.isNotBlank(parentId)) {
			model.addAttribute("parentOrg", this.generalManager.get(
					Organization.class, parentId));
		}
//
//		queryModel.setSelect("sum(alias.idx)");
//		// queryModel.add(ConditionModel.isNotNull("persons.age"));
//		// queryModel.setGroupby("parentOrg.id");
//
//		List list = this.generalManager.find(queryModel);
//
//		model.addAttribute("list", list);

		saveReferer(request);
		return "/admin/orgList";
	}

}
