package com.poscodx.mysite.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class BoardActionFactory implements ActionFactory {
	Action action = null;
	@Override
	public Action getAction(String actionname) {
		if("writeform".equals(actionname)) {
			action = new WriteFormAction();
		}
		else if("write".equals(actionname)) {
			action = new WriteAction();
		} 
		else if("view".equals(actionname)) {
			action = new ViewAction();
		}
		else if("modifyform".equals(actionname)) {
			action = new ModifyFormAction();
		}
		else if("modify".equals(actionname)) {
			action = new ModifyAction();
		}
		else {
			action = new BoardAction();
		}
		return action;
	}

}
