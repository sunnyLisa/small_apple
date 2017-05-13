#include<defts201.h>
#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<FFTDef.h>
#include<sysreg.h>


#define P 8192
#define M 7680


/*4字节对齐*/

#pragma align 4
section("data2a")
float echo_re[P] = {
	#include "echo_re.dat"
};

#pragma align 4
section("data2a")
float echo_im[P] = {
	#include "echo_im.dat"
};

#pragma align 4
section("data2a")
float echo[2*P];

#pragma align 4
section("data2a")
float echo_fft[2*P];

#pragma align 4
section("data2a")
float temp[2*P];

#pragma align 4
section("data6a")
float coeff_fft_re[P] ={
	#include"coeff_fft_re.dat"
};

#pragma align 4
section("data6a")
float coeff_fft_im[P] = {
	#include"coeff_fft_im.dat"
};

#pragma align 4
section("data6a")
float coeff_fft[2*P];

#pragma align 4
section("data6a")
float buffer1[2*P];

#pragma align 4
section("data6a")
float buffer2[2*P];


#pragma align 256
section("data2a")
float freq_press_im[P];


#pragma align 256
section("data2a")
float freq_press_re[P];

#pragma align 256
section("data2a")
float freq_press[2*P];

#pragma align 256
section("data2a")
float freq_press_abs[M];

#pragma align 4
section("data4a")
float mti[16*480*2];



#pragma align 4
section("data4a")
float mti_abs[M-480];

#pragma align 4
section("data2a")
float input[32];

#pragma align 4
section("data2a")
float output[32];

#pragma align 4
section("data2a")
float mtd[960+32*480];

#pragma align 4
section("data2a")
float mtd_abs[M];




/*** 变量预定义部分完成，注意  *****/











/*函数声明*/
extern fft_flp32(float *,float *,float *,float *);
extern fft_16(float*,float*);

/*main*/
main()
{
  int m,n;
  
  /**********分别将回拨和脉压系数实部，虚部放在一起*********/
  for(m=0;m<P*2;m+=2)
  {
  	echo[m]=echo_re[m/2];
  	echo[m+1]=echo_im[m/2];
  }	
  
  for(m=0;m<P*2;m+=2)
  {
  	coeff_fft[m]=coeff_fft_re[m/2];
  	coeff_fft[m+1]=coeff_fft_im[m/2];
  }
  
  /*回拨8192点FFT变换*/
  fft_flp32(echo,buffer1,buffer2,echo_fft);
  
  
  /*频率脉冲压缩  点乘*/
  for(m=0;m<P*2;m+=2)
  {
  	temp[m] = echo_fft[m]*coeff_fft[m]-echo_fft[m+1]*coeff_fft[m+1];
  	temp[m+1] = echo_fft[m]*coeff_fft[m+1]+echo_fft[m+1]*coeff_fft[m];
  }
  
  /*对频率脉压进行ifft*/  /*注意这段代码没写完整，或者没写全，也有可能写错，注意核查*/
  /* 这段代码的核心思想是用fft函数，实现ifft()功能 */
  /*对temp数据进行ifft变换  */
  /* 取temp的共轭，虚部取反     */
  for(m=0;m<P*2;m+=2)
  {
  	temp[m]=0-temp[m];
  
  }
  
  fft_flp32(temp,buffer1,buffer2,freq_press);
  for(m=0;m<p*2;m+=1)
  {
  	freq_press[m]=freq_press[m]/P;
  	freq_press[m+1]=0-freq_press[m+1]/P;/* 输出数据求 复共轭，完成fft实现ifft   */
  
  }
  
  /****  去除暂态点，强制转换成7680点******   */
  
  for(m=83*2;m<(M+83)*2;m++)
  {
  	freq_press[m-83*2] = freq_press[m];
  }
  for(n=0;n<M*2;n+=2)
  {
  	freq_press_re[m/2]=freq_press[m];
  	freq_press_im[m/2]=freq_press[m+1];
  }
  for(m=0;m<M;m++)
  {
  	freq_press_abs[m]=sqrtf(freq_press_re[m]*freq_press_re[m]+freq_press_im[m]*freq_press_im[m]);
  }
  
  /**** 动态目标显示MTI(对消)  *****/
  for(n=0;n<15;n++)
  {
  	for(m=n*480*2;m<(n+1)*480*2;m+=2)
  	{
  		mti[m] = freq_press[m+480*2]-freq_press[m];
  		mti[m+1] = freq_press[m+1+480*2]-freq_press[m+1];
  	}
  }
  
  for(m=0;m<M-480;m++)
  {
  	mti_abs[m] = sqrtf(mti[m*2]*mti[m*2]+mti[m*2+1]*mti[m*2+1]);
  }
  /***** 未完成   */
  
  
  
  
  
  
  
  
  /*****动态目标检查MTD*******/
  for(n=0;n<960;n+=2)
  {
  	for(m=0;m<32;m+=2)
  	{
  		input[m] = freq_press[n+m*480];
  		input[m+1] = freq_press[n+1+m*480];
  	}
  	fft_16(input,output);
  	for(m=0;m<32;m+=2)
  	{
  		mtd[n+m+480]= output[m];
  		mtd[n+1+m*480] = output[m+1];
  	}
  }
  
  
  for(m=0;m<M;m++)
  {
  	mtd_abs[m] = sqrtf(mtd[m*2]*mtd[m*2]+mtd[m*2+1]*mtd[m*2+1]);
  }
  
  
  
  
  /*****将DSP中的数据导入到matlab中*****/   /*这几个文件都要自己建*/
  
  FILE *fp
  if((fp=fopen("e:\\work\\DSP\\JDQ\\freq_press_abs.dat","wb"))==NULL)
  {
  	printf("connot open dsp_press_output.dat\n");
  }
  else
  {
  	fwrite(freq_press_abs,M,1,fp);
  	fclose(fp);
  }
  
   if((fp=fopen("e:\\work\\DSP\\JDQ\\mti_out.dat","wb"))==NULL)
  {
     printf("connot open dsp_mti_output.dat\n");
  }
  else
  {
  	 fwrite(mti_abs,7200,1,fp);
     fclose(fp);
  }
  
  if((fp=fopen("e:\\work\\DSP\\JDQ\\mtd_out.dat","wb"))==NULL)
  {
     printf("connot open dsp_mtd_output.dat\n");
  }
  else
  {
  	 fwrite(mtd_abs,7200,1,fp);
     fclose(fp);
  }
}
  
  
  
  
  	
