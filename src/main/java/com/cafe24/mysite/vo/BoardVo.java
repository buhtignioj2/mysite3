package com.cafe24.mysite.vo;

public class BoardVo {
    private Long no;
    private String title;
    private String content;
    private Integer groupNo;
    private Integer orderNo;
    private Integer depth;
    private Integer hit;
    private String regDate;
    private String userName;
    private Long userNo;
    
    @Override
    public String toString() {
	return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", groupNo=" + groupNo + ", orderNo="
		+ orderNo + ", depth=" + depth + ", hit=" + hit + ", regDate=" + regDate + ", userName=" + userName
		+ ", userNo=" + userNo + "]";
    }
    public Long getNo() {
        return no;
    }
    public void setNo(Long no) {
        this.no = no;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getGroupNo() {
        return groupNo;
    }
    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }
    public Integer getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    public Integer getDepth() {
        return depth;
    }
    public void setDepth(Integer depth) {
        this.depth = depth;
    }
    public Integer getHit() {
        return hit;
    }
    public void setHit(Integer hit) {
        this.hit = hit;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getUserNo() {
        return userNo;
    }
    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

}
