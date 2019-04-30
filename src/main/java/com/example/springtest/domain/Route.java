package com.example.springtest.domain;

import javax.persistence.*;

public class Route {
    @Column(name = "route_id")
    private Integer routeId;

    /**
     * 路线具体信息
     */
    @Column(name = "route_content")
    private String routeContent;

    @Column(name = "journey_id")
    private Integer journeyId;

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "end_point")
    private String endPoint;

    /**
     * 是否为出发地的路线信息
     */
    @Column(name = "isOrigin")
    private Integer isorigin;

    /**
     * @return route_id
     */
    public Integer getRouteId() {
        return routeId;
    }

    /**
     * @param routeId
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    /**
     * 获取路线具体信息
     *
     * @return route_content - 路线具体信息
     */
    public String getRouteContent() {
        return routeContent;
    }

    /**
     * 设置路线具体信息
     *
     * @param routeContent 路线具体信息
     */
    public void setRouteContent(String routeContent) {
        this.routeContent = routeContent;
    }

    /**
     * @return journey_id
     */
    public Integer getJourneyId() {
        return journeyId;
    }

    /**
     * @param journeyId
     */
    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    /**
     * @return start_point
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * @param startPoint
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * @return end_point
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * @param endPoint
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * 获取是否为出发地的路线信息
     *
     * @return isOrigin - 是否为出发地的路线信息
     */
    public Integer getIsorigin() {
        return isorigin;
    }

    /**
     * 设置是否为出发地的路线信息
     *
     * @param isorigin 是否为出发地的路线信息
     */
    public void setIsorigin(Integer isorigin) {
        this.isorigin = isorigin;
    }
}