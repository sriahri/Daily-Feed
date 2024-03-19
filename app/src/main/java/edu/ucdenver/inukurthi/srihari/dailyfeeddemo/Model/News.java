package edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable {

    private String status;
    private int totalResults;
    private List<Headlines> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Headlines> getArticles() {
        return articles;
    }

    public void setArticles(List<Headlines> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "News{" + "status='" + status + '\'' + ", totalResults=" + totalResults + ", articles=" + articles + '}';
    }

}
