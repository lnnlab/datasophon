package com.datasophon.worker.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.datasophon.common.Constants;
import com.datasophon.common.utils.PropertyUtils;

public class KerberosUtils {

    public static void downloadKeytabFromMaster(String principal, String keytabName) {
        String masterHost = PropertyUtils.getString(Constants.MASTER_HOST);
        String masterPort = PropertyUtils.getString(Constants.MASTER_WEB_PORT);
        Integer clusterId = PropertyUtils.getInt("clusterId");

        //get kerberos keytab
        String downloadUrl = "http://" + masterHost + ":" + masterPort + "/ddh/cluster/kerberos/downloadKeytab?clusterId="
                + clusterId + "&principal=" + principal + "&keytabName=" + keytabName;

        String dest = "/etc/security/keytab/";
        HttpUtil.downloadFile(downloadUrl, FileUtil.file(dest), new StreamProgress() {
            @Override
            public void start() {
                Console.log("start to install。。。。");
            }

            @Override
            public void progress(long progressSize, long l1) {
                Console.log("installed：{}", FileUtil.readableFileSize(progressSize));
            }

            @Override
            public void finish() {
                Console.log("install success！");
            }
        });
    }
}
