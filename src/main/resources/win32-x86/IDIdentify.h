// IDIdentify.h : IDIdentify DLL ����ͷ�ļ�
//

#pragma once

#ifndef __AFXWIN_H__
	#error "�ڰ������ļ�֮ǰ������stdafx.h�������� PCH �ļ�"
#endif

#include "resource.h"		// ������


// CIDIdentifyApp
// �йش���ʵ�ֵ���Ϣ������� IDIdentify.cpp
//

class CIDIdentifyApp : public CWinApp
{
public:
	CIDIdentifyApp();

// ��д
public:
	virtual BOOL InitInstance();

	DECLARE_MESSAGE_MAP()
};



//1	��ȡ��ά��Ӧ������
struct  ApplyData  
{
	unsigned short type;                    //type��IN��               �������ͣ�ȡֵ{0��1}
    unsigned char  organizeId[8];           //organizeId��IN��         ����ID��8�ֽ�
    unsigned short organizeIdLen;           //organizeIdLen��IN��      ����ID����
    unsigned char  appId[4];                //appId��IN��              Ӧ��ID��4�ֽ�
    unsigned short appIdLen;                //appIdLen��IN��           Ӧ��ID����
};
unsigned char getApplyData (struct  ApplyData *Apply,
                            unsigned char *applyData,unsigned short* applyDataLen);
//Apply��IN��                 ApplyData�ṹ��  
//ApplyData��OUT��            ��ά����֤��������
//ApplyDataLen��OUT��         ��ά����֤�������ݳ���


//2	��ȡ��ά�븳������
struct ReqCodeData  
{
    unsigned char  ctid[512];               //ctid��IN��               ��֤����
    unsigned short ctidLen;                 //ctidLen��IN��            ��֤���ݳ���
    unsigned char  organizeId[8];           //organizeId��IN��         ����ID��8�ֽ�
    unsigned short organizeIdLen;           //organizeIdLen��IN��      ����ID����
    unsigned char  appId[4];                //appId��IN��              Ӧ��ID��4�ֽ�
    unsigned short appIdLen;                //appIdLen��IN��           Ӧ��ID����
};
unsigned char getReqQRCodeData (unsigned char *randomNumber,unsigned short randomNumberLen,
                                struct  ReqCodeData *ReqCode,
                                unsigned char *reqData,unsigned short *reqDataLen);
//randomNumber��IN��          �����     
//randomNumberLen��IN��       ���������   
//ReqCode��IN��               ReqCodeData�ṹ��  
//reqData��OUT��              ��֤��������
//reqDataLen��OUT��           ��֤�������ݳ���


//3��ȡ��ά����������
struct QRCodeData  
{
    unsigned char  qrCode[4096];            //qrCode��IN��             ��ά������
    unsigned short qrCodeLen;               //qrCodeLen��IN��          ��ά�����ݳ���
    unsigned char  organizeId[8];           //organizeId��IN��         ����ID��8�ֽ�
    unsigned short organizeIdLen;           //organizeIdLen��IN��      ����ID����
    unsigned char  appId[4];                //appId��IN��              Ӧ��ID��4�ֽ�
    unsigned short appIdLen;                //appIdLen��IN��           Ӧ��ID����
};
unsigned char getAuthQRCodeData (unsigned char *randomNumber,unsigned short randomNumberLen,
                                 struct  QRCodeData  *QRCode,
                                 unsigned char *qrData,unsigned short *qrDataLen);
//randomNumber��IN��          �����
//randomNumberLen��IN��       ���������
//QRCode��IN��                QRCodeData�ṹ��  
//qrData��OUT��               ��֤��������
//qrDataLen��OUT��            ��֤�������ݳ���


//4	��ȡ��֤��������
unsigned char getAuthCodeData (unsigned char *randomNumber, unsigned short randomNumberLen,unsigned short btime,
                               unsigned char *authCode,     unsigned short *authCodeLen);
//randomNumber��IN��         �����֤����Ӧ�������е������
//randomNumberLen��IN��      ���������
//btime��IN��                ��֤���������չʾʱ�䣬ȡֵ��Χ5-100
//authCode��OUT��            ��֤��������
//authCodeLen��OUT��         ��֤�������ݳ���


//5ȡID��֤����
struct IdCardData
{
    unsigned short type;                   //type��IN���������ͣ�ȡֵ{0��1��2��3}
    unsigned char  ctid[512];              //ctid                     ��֤����
    unsigned short ctidLen;                //ctidLen                  ��֤���ݳ���
    unsigned char  organizeId[8];          //organizeId��IN��         ����ID��8�ֽ�
    unsigned short organizeIdLen;          //organizeIdLen��IN��      ����ID����
    unsigned char  appId[4];               //appId��IN��              Ӧ��ID��4�ֽ�
    unsigned short appIdLen;               //appIdLen��IN��           Ӧ��ID����
    unsigned short Vid;                    //Vid��IN��                ��USB�ӿڶ�����ʱ���豸��Ӧ��ID
    unsigned short Pid;                    //Pid��IN��                ��USB�ӿڶ�����ʱ���豸��ƷID
    unsigned short Port;                   //Port��IN��               ��USB�ӿڶ�����ʱ���˿ں�Ϊ0
};
unsigned char getAuthIDCardData( unsigned char *randomNumber,unsigned short randomNumberLen,
                                 struct IdCardData *IdCard,
                                 unsigned char *idData,      unsigned short *idDataLen);
//randomNumber��IN��       �����
//randomNumberLen��IN��    ���������
//IdCard��IN��             IdCardData�ṹ��  
//idData��OUT��            ID��֤����
//idDataLen��OUT��         ID��֤���ݳ���


//6	��ȡ�����ؼ��汾
void getAuthIDCardDataVer (unsigned char *ver);
//ver��OUT��               �����ؼ��汾


//7 ��ȡ��ȡ��֤����
struct CtidNum
{
    unsigned char  ctidNum[9];                //ctidNum��OUT��               ��֤���
    unsigned short ctidNumLen;                //ctidNumLen��OUT��           ��֤��ų���
    unsigned char  validDate[8];              //validDate��OUT��            ��Ч��
    unsigned short validDateLen;              //validDateLen��OUT��         ��Ч�ڳ���
};
unsigned char getCtidNum (unsigned char *ctid,unsigned short ctidLen,
                          struct CtidNum *ctidnum);
//ctid��IN��               ��֤����
//ctidLen��IN��            ��֤���ݳ���
//ctidnum��OUT��           CtidNum�ṹ��


//8 ��ȡ��ά��ͼƬ����
unsigned char creatQRCodeImage(unsigned char *imgStream,unsigned short imgStreamLen,
                               unsigned short width,unsigned short nWid);
//imgStream��IN��          ���������ȡ����imgStream
//imgStreamLen��IN��       imgStream����  
//width��IN��              ���������ȡ����width
//nWid��IN��               ��ά��ͼƬ�ķŴ�����ȡֵ��Χ1-8���Ƽ�ȡֵ4��5