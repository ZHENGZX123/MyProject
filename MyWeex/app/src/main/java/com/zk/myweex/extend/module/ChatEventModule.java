package com.zk.myweex.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Created by Administrator on 2017/3/24.
 */

public class ChatEventModule extends WXModule {
    @JSMethod()
    public void creatGroup(String str, JSCallback callback) {
        Log.d("test", "creatGroup = " + str);

        // {"groupList":[{"nickname":"(╥﹏23","uid":"247"},{"nickname":"Mybaby","uid":"4637abf00aed11e78ad90959ab5c0b23"}],"groupName":"tt","classId":"56"}

//        NSMutableDictionary *groupInfo = [[NSMutableDictionary alloc]init];
//        [groupInfo setValue:groupName forKey:@"name"];
//        [groupInfo setValue:@"" forKey:@"icon"];
//        [groupInfo setValue:@"" forKey:@"notice"];
//        [groupInfo setValue:@"" forKey:@"intro"];
//        [groupInfo setValue:@"" forKey:@"type"];
//        [groupInfo setValue:@"" forKey:@"ispublic"];
//        [groupInfo setValue:@"" forKey:@"isvalidate"];
//        [groupInfo setValue:@"1000" forKey:@"maxnum"];
//        NSString *groupinfo = [ICTools JsonStringWithdictionary:groupInfo];
//        NSLog(@"%@",groupinfo);
//
//        NSArray *grouplist = dic[@"groupList"];
//        NSString * friendList = [ICTools JsonStringWithdictionary:grouplist];
//
//        HproseMqttClient *client = [HproseMqttClient shared ];
//        id tk = [client getToken];
//        if ([Promise isPromise:tk]) {
//            [((Promise *)tk) then:^id(NSString *tk) {
//                NSLog(@"-------gettkoken = %@",tk);
//
//            /*发送命令接收结果，在Exam 协议中添加方法*/
//                MsgDelegate = [client useService:@protocol (HproseMsgDelegate)];
//                id ret = [MsgDelegate creatGroup:groupinfo andfriendlist:friendList];
//                if ([Promise isPromise:ret]) {
//                    [((Promise *)ret) then:^id(NSString *ret) {
//                        NSDictionary *dic =[ICTools dictionaryWithJsonString:ret];
//                        NSArray *data = [dic objectForKey:@"data"];
//
//                        callback(@{@"result":@"1"});
//                        return nil;
//                    }];
//                }
//                else if ([ret isKindOfClass:[NSString class]]) {
//                    NSLog(@"----------------%@",ret);
//                }
//                else if ([ret isKindOfClass:[NSException class]]) {
//                    NSLog(@"----------------%@",ret);
//
//                }
//            /* 获取 token 跳转页面 */
//                return nil;
//            }];
//        }
    }
}
