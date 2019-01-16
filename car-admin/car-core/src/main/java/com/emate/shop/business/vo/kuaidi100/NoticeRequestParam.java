package com.emate.shop.business.vo.kuaidi100;

public class NoticeRequestParam {

	/*监控状态:
	 * polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。
	 * 其中当快递单为已签收时status=shutdown，
	 * 当message为“3天查询无记录”或“60天无变化时”status= abort ，
	 * 对于stuatus=abort的状度，需要增加额外的处理逻辑，详见本节最后的说明 */
    private String status     = "";

    private String billstatus = "";

    private String message    = "";
    
    private String autoCheck = "";
    
    private String comNew = "";

	private String comOld = "";

	private Result lastResult = new Result();

	public String getAutoCheck() {
		return autoCheck;
	}

	public String getBillstatus() {
        return billstatus;
    }

	public String getComNew() {
		return comNew;
	}

	public String getComOld() {
		return comOld;
	}
    
    public Result getLastResult() {
        return lastResult;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setAutoCheck(String autoCheck) {
		this.autoCheck = autoCheck;
	}

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public void setComNew(String comNew) {
		this.comNew = comNew;
	}

    public void setComOld(String comOld) {
		this.comOld = comOld;
	}

    public void setLastResult(Result lastResult) {
        this.lastResult = lastResult;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
