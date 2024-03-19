package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import java.util.List;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Headlines;

public interface DataListener<News> {

    void onFetchData(List<Headlines> headlines, String message);

    void onError(String message);
}
