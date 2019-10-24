package ru.qupol;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InitService {

    InitService(@Value("${spring.mvc.view.prefix}") String text) {
        System.out.println(text);
    }
}
