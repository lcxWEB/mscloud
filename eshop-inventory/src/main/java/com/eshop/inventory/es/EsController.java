package com.eshop.inventory.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author wangzu
 * @date 2019/2/13
 */
@RestController
@RequestMapping("api/es")
public class EsController {

    @Autowired
    private EsService esService;

    @GetMapping("createIndex")
    public boolean createIndex() {
        boolean res = esService.createIndex();
        return res;
    }

    @GetMapping("addAllBaseInfo")
    public void addAllBaseInfo() {
        try {
            esService.addAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("delIndex")
    public void delIndex() {
        try {
            esService.indexDelete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("reIndex")
    public void reIndex() {
        try {
            boolean exist = esService.checkIndexExist();
            if (exist) {
                esService.indexDelete();
            }
            esService.createIndex();
            esService.setMapping();
            esService.addAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
