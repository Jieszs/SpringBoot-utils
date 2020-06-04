// IDIdentify.h : IDIdentify DLL 的主头文件
//

#pragma once

#ifndef __AFXWIN_H__
	#error "在包含此文件之前包含“stdafx.h”以生成 PCH 文件"
#endif

#include "resource.h"		// 主符号


// CIDIdentifyApp
// 有关此类实现的信息，请参阅 IDIdentify.cpp
//

class CIDIdentifyApp : public CWinApp
{
public:
	CIDIdentifyApp();

// 重写
public:
	virtual BOOL InitInstance();

	DECLARE_MESSAGE_MAP()
};



//1	获取二维码应用数据
struct  ApplyData  
{
	unsigned short type;                    //type【IN】               操作类型，取值{0，1}
    unsigned char  organizeId[8];           //organizeId【IN】         机构ID，8字节
    unsigned short organizeIdLen;           //organizeIdLen【IN】      机构ID长度
    unsigned char  appId[4];                //appId【IN】              应用ID，4字节
    unsigned short appIdLen;                //appIdLen【IN】           应用ID长度
};
unsigned char getApplyData (struct  ApplyData *Apply,
                            unsigned char *applyData,unsigned short* applyDataLen);
//Apply【IN】                 ApplyData结构体  
//ApplyData【OUT】            二维码认证申请数据
//ApplyDataLen【OUT】         二维码认证申请数据长度


//2	获取二维码赋码数据
struct ReqCodeData  
{
    unsigned char  ctid[512];               //ctid【IN】               网证数据
    unsigned short ctidLen;                 //ctidLen【IN】            网证数据长度
    unsigned char  organizeId[8];           //organizeId【IN】         机构ID，8字节
    unsigned short organizeIdLen;           //organizeIdLen【IN】      机构ID长度
    unsigned char  appId[4];                //appId【IN】              应用ID，4字节
    unsigned short appIdLen;                //appIdLen【IN】           应用ID长度
};
unsigned char getReqQRCodeData (unsigned char *randomNumber,unsigned short randomNumberLen,
                                struct  ReqCodeData *ReqCode,
                                unsigned char *reqData,unsigned short *reqDataLen);
//randomNumber【IN】          随机数     
//randomNumberLen【IN】       随机数长度   
//ReqCode【IN】               ReqCodeData结构体  
//reqData【OUT】              认证申请数据
//reqDataLen【OUT】           认证申请数据长度


//3获取二维码验码数据
struct QRCodeData  
{
    unsigned char  qrCode[4096];            //qrCode【IN】             二维码数据
    unsigned short qrCodeLen;               //qrCodeLen【IN】          二维码数据长度
    unsigned char  organizeId[8];           //organizeId【IN】         机构ID，8字节
    unsigned short organizeIdLen;           //organizeIdLen【IN】      机构ID长度
    unsigned char  appId[4];                //appId【IN】              应用ID，4字节
    unsigned short appIdLen;                //appIdLen【IN】           应用ID长度
};
unsigned char getAuthQRCodeData (unsigned char *randomNumber,unsigned short randomNumberLen,
                                 struct  QRCodeData  *QRCode,
                                 unsigned char *qrData,unsigned short *qrDataLen);
//randomNumber【IN】          随机数
//randomNumberLen【IN】       随机数长度
//QRCode【IN】                QRCodeData结构体  
//qrData【OUT】               认证申请数据
//qrDataLen【OUT】            认证申请数据长度


//4	获取网证口令数据
unsigned char getAuthCodeData (unsigned char *randomNumber, unsigned short randomNumberLen,unsigned short btime,
                               unsigned char *authCode,     unsigned short *authCodeLen);
//randomNumber【IN】         身份认证申请应答数据中的随机数
//randomNumberLen【IN】      随机数长度
//btime【IN】                网证口令输入框展示时间，取值范围5-100
//authCode【OUT】            网证口令数据
//authCodeLen【OUT】         网证口令数据长度


//5取ID验证数据
struct IdCardData
{
    unsigned short type;                   //type【IN】操作类型，取值{0，1，2，3}
    unsigned char  ctid[512];              //ctid                     网证数据
    unsigned short ctidLen;                //ctidLen                  网证数据长度
    unsigned char  organizeId[8];          //organizeId【IN】         机构ID，8字节
    unsigned short organizeIdLen;          //organizeIdLen【IN】      机构ID长度
    unsigned char  appId[4];               //appId【IN】              应用ID，4字节
    unsigned short appIdLen;               //appIdLen【IN】           应用ID长度
    unsigned short Vid;                    //Vid【IN】                接USB接口读卡器时，设备供应商ID
    unsigned short Pid;                    //Pid【IN】                接USB接口读卡器时，设备产品ID
    unsigned short Port;                   //Port【IN】               接USB接口读卡器时，端口号为0
};
unsigned char getAuthIDCardData( unsigned char *randomNumber,unsigned short randomNumberLen,
                                 struct IdCardData *IdCard,
                                 unsigned char *idData,      unsigned short *idDataLen);
//randomNumber【IN】       随机数
//randomNumberLen【IN】    随机数长度
//IdCard【IN】             IdCardData结构体  
//idData【OUT】            ID验证数据
//idDataLen【OUT】         ID验证数据长度


//6	获取读卡控件版本
void getAuthIDCardDataVer (unsigned char *ver);
//ver【OUT】               读卡控件版本


//7 获取获取网证数据
struct CtidNum
{
    unsigned char  ctidNum[9];                //ctidNum【OUT】               网证编号
    unsigned short ctidNumLen;                //ctidNumLen【OUT】           网证编号长度
    unsigned char  validDate[8];              //validDate【OUT】            有效期
    unsigned short validDateLen;              //validDateLen【OUT】         有效期长度
};
unsigned char getCtidNum (unsigned char *ctid,unsigned short ctidLen,
                          struct CtidNum *ctidnum);
//ctid【IN】               网证数据
//ctidLen【IN】            网证数据长度
//ctidnum【OUT】           CtidNum结构体


//8 获取二维码图片数据
unsigned char creatQRCodeImage(unsigned char *imgStream,unsigned short imgStreamLen,
                               unsigned short width,unsigned short nWid);
//imgStream【IN】          赋码请求获取到的imgStream
//imgStreamLen【IN】       imgStream长度  
//width【IN】              赋码请求获取到的width
//nWid【IN】               二维码图片的放大倍数，取值范围1-8，推荐取值4、5