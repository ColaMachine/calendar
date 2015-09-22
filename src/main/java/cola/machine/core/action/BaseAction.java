package cola.machine.core.action;

import cola.machine.core.msg.ErrorMessage;
import cola.machine.core.page.Page;

public class BaseAction {
    /*
	protected WifiResultDTO getWifiResult(String resultcode,String message){
        return new WifiResultDTO(resultcode,message);
    }*/
    
	/**
	 * 返回成功，默认代码1
	 * @return
	 * @author 宋展辉
	 */
    protected ResultDTO getResult(){
        return this.getResult("1", null, null,null);
    }
    
    /**
     * 返回成功，代码result
     * @param result
     * @return
     * @author 宋展辉
     */
    protected ResultDTO getResult(String result){
        return getResult(result, null, null,null);
    }
    
	/**
	 * 返回成功，数据data
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(Object data){
		return getResult("1", data, null,null);
	}
	
	/**
	 * 返回成功，数据data,分页page
	 * @param data
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(Object data,Page page){
		return getResult("1", data, null,page);
	}
	
	/**
	 * 返回错误请求，错误代码result，错误说明msg
	 * @param result
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(String result, String msg){
        return getResult(result, null, msg);
    }
	
	/**返回错误请求，根据错误代码名code获取错误代码及说明
	 * @param code
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getWrongResultFromCfg(String code){
		String result = ErrorMessage.getErrorMsgCode(code);
		String msg = ErrorMessage.getErrorMsg(code);
		if(result==null||msg==null){
			result ="999";
			msg = "错误代码名不存在";
		}
		return getResult(result, msg);
		
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(String result, Object data, String msg){
		return getResult(result, data, msg,null);
	}
	
	/**
	 * 自定义返回
	 * @param result
	 * @param data
	 * @param msg
	 * @param page
	 * @return
	 * @author 宋展辉
	 */
	protected ResultDTO getResult(String result, Object data, String msg , Page page){
        return new ResultDTO(result, data, msg, page);
    }
}