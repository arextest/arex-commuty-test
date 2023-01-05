package com.arextest.agent.test.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class DynamicService {
    public String readFile(String fileName) {
        File file = new File(fileName);
        throw new RuntimeException(fileName + "FileNotFoundException 2");
    }
}
