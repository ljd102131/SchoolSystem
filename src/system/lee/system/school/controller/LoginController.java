package lee.system.school.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.MD5Encoder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lee.system.school.common.MD5Operation;
import lee.system.school.entity.*;
import lee.system.school.service.impl.UserService;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/system")
public class LoginController {

	@Autowired
	public UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		System.out.println("进入啦");
		return "login2";
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseResult login(User user, HttpServletRequest request) {
		ResponseResult responseResult = new ResponseResult(ResponseResult.FAILURECODE,"登陆失败");
		String loginName = user.getLoginName();
		String passWord = user.getPassWord();
		String eccodePassWord = MD5Operation.getEncryptedPwd(passWord);
		
		/*调用shiro判断当前用户是否是系统用户*/
		//得到当前用户
		Subject subject = SecurityUtils.getSubject();
		//判断是否登录，如果未登录，则登录
		if (!subject.isAuthenticated()) {
			//创建用户名/密码验证Token, shiro是将用户录入的登录名和密码（未加密）封装到uPasswordToken对象中
			UsernamePasswordToken uPasswordToken = new UsernamePasswordToken(loginName,eccodePassWord);
			//自动调用AuthRealm.doGetAuthenticationInfo
			try {
				//执行登录，如果登录未成功，则捕获相应的异常
				subject.login(uPasswordToken);
				responseResult.setMsg("登录成功");
				responseResult.setCode(ResponseResult.SUCCESSCODE);
			}catch (Exception e) {
				// 捕获异常
			}
		}
				
		/*写seesion，保存当前user对象*/
		//从shiro中获取当前用户
		User sUser = (User)subject.getPrincipal();
        subject.getSession().setAttribute("sUser", sUser);
        if (sUser != null) {
        	responseResult.setMsg("登录成功");
			responseResult.setCode(ResponseResult.SUCCESSCODE);
		}
        
        File file = new File("D://Java//DoWork//pollution1.xls");  
        readExcel(file);
		return responseResult;
	}
	
	public void readExcel(File file) {
		try {  
			//读取excel文件内容
            List<ExcelPollution> listPollution = new ArrayList<>();
            List<ExcelClinic> listClinic = new ArrayList<>();
            // 创建输入流，读取Excel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl提供的Workbook类  
            Workbook wb = Workbook.getWorkbook(is);  
            // Excel的页签数量  
            //int sheet_size = wb.getNumberOfSheets();  
            //for (int index = 0; index < sheet_size; index++)
            {  
                // 每个页签创建一个Sheet对象  
                Sheet sheet = wb.getSheet(0);
                // sheet.getRows()返回该页的总行数  
                for (int i = 0; i < sheet.getRows(); i++) { 
                    ExcelPollution excelPollution = new ExcelPollution(); 
                    // sheet.getColumns()返回该页的总列数  
                    for (int j = 0; j < sheet.getColumns(); j++) {  
                        String cellinfo = sheet.getCell(j, i).getContents();  
                        if (j == 0) {
                            excelPollution.setLocation(cellinfo);
						}
                        else if (j == 1) {
                        	String dString = cellinfo;
                        	if (i > 0) {
                        		dString = "20" + cellinfo.replace("-", "/");
							}
                            excelPollution.setDate(dString);
						}
                        else if (j == 2) {
                            excelPollution.setSO2(cellinfo);
						}
                        else if (j == 3) {
                            excelPollution.setNO2(cellinfo);
						}
                        else if (j == 4) {
                            excelPollution.setPM10(cellinfo);
						}
                        else if (j == 5) {
                            excelPollution.setCO(cellinfo);
						}
                        else if (j == 6) {
                            excelPollution.setO3(cellinfo);
						}
                        else if (j == 7) {
                            excelPollution.setPM25(cellinfo);
						} 
                    }  
                    listPollution.add(i, excelPollution);
                }  
            }  
            
            File file2 = new File("D://Java//DoWork//clinic1.xls");  
            // 创建输入流，读取Excel  
            InputStream is2 = new FileInputStream(file2.getAbsolutePath());  
            // jxl提供的Workbook类  
            Workbook wb2 = Workbook.getWorkbook(is2);  
            // Excel的页签数量  
            //int sheet_size2 = wb2.getNumberOfSheets();  
            //for (int index = 0; index < sheet_size2; index++) 
            {  
                // 每个页签创建一个Sheet对象  
                Sheet sheet2 = wb2.getSheet(1);
                // sheet.getRows()返回该页的总行数  
                for (int i = 0; i < sheet2.getRows(); i++) { 
                    ExcelClinic excelClinic = new ExcelClinic(); 
                    // sheet.getColumns()返回该页的总列数  
                    for (int j = 0; j < sheet2.getColumns(); j++) {  
                        String cellinfo = sheet2.getCell(j, i).getContents();  
                        if (j == 0) {
                        	excelClinic.setStation(cellinfo);
						}
                        else if (j == 1) {
                        	String dString = cellinfo;
                        	if (i > 0) {
                        		dString = "20" + cellinfo.replace("-", "/");
							}
                        	excelClinic.setDate(dString);
						}
                        else if (j == 2) {
                        	excelClinic.setSO2(cellinfo);
						}
                        else if (j == 3) {
                        	excelClinic.setNO2(cellinfo);
						}
                        else if (j == 4) {
                        	excelClinic.setPM10(cellinfo);
						}
                        else if (j == 5) {
                        	excelClinic.setCO(cellinfo);
						}
                        else if (j == 6) {
                        	excelClinic.setO3(cellinfo);
						}
                        else if (j == 7) {
                        	excelClinic.setPM25(cellinfo);
						}
                        else if (j == 8) {
                        	excelClinic.setDiagnose(cellinfo);
						}
                        else if (j == 9) {
                        	excelClinic.setICD_10(cellinfo);
						}
                        else if (j == 10) {
                        	String dString = cellinfo;
                        	if (i > 0) {
                        		dString = "20" + cellinfo.replace("-", "/");
							}
                        	excelClinic.setDate_birth(dString);
						}
                        else if (j == 11) {
                        	excelClinic.setAge_0(cellinfo);
						}
                        else if (j == 12) {
                        	excelClinic.setGender(cellinfo);
						}
                        else if (j == 13) {
                        	excelClinic.setAge_1(cellinfo);
						}
                        else if (j == 14) {
                        	String dString = "20" + cellinfo.replace("-", "/");
                        	excelClinic.setDate_discharge(dString);
						}
                        else if (j == 15) {
                        	excelClinic.setRegisted_address(cellinfo);
						}
                        else if (j == 16) {
                        	excelClinic.setAdmission_cost(cellinfo);
						}
                        else if (j == 17) {
                        	excelClinic.setAddress_present(cellinfo);
						}
                        else if (j == 18) {
                        	excelClinic.setICD(cellinfo);
						}
                        else if (j == 19) {
                        	excelClinic.setID(cellinfo);
						}
                    }
                    
                    listClinic.add(i, excelClinic);
                }  
            }  
            
            if (listClinic.size() >0 && listPollution.size() > 0) {
            	for (ExcelClinic clinic : listClinic) {
            		for (ExcelPollution pollution : listPollution) {
						if (pollution.getLocation().equals(clinic.getStation()) && pollution.getDate().equals(clinic.getDate())) {
							clinic.setSO2(pollution.getSO2());
							clinic.setNO2(pollution.getNO2());
							clinic.setPM10(pollution.getPM10());
							clinic.setCO(pollution.getCO());
							clinic.setO3(pollution.getO3());
							clinic.setPM25(pollution.getPM25());
							try {
		                    	if (pollution.getPM10().equals("PM10") && pollution.getPM25().equals("PM2.5")) {
		                    		clinic.setPMc("PMc");
								}
		                    	else if (!pollution.getPM10().equals("") && !pollution.getPM25().equals("") && pollution.getPM10() != null && pollution.getPM25() != null) {
									Integer pMc = Integer.parseInt(clinic.getPM25()) - Integer.parseInt(clinic.getPM10());
									clinic.setPMc(pMc.toString());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
                      
            //按照对象中的date日期进行排序
            Collections.sort(listClinic, new Comparator<ExcelClinic>() {
                @Override
                public int compare(ExcelClinic o1, ExcelClinic o2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        Date dt1 = format.parse(o1.getDate());
                        Date dt2 = format.parse(o2.getDate());
                        if (dt1.getTime() > dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() < dt2.getTime()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

            //写入excel文件
            try {  
                // 打开文件  
                WritableWorkbook book = Workbook.createWorkbook(new File("D://Java//DoWork//test.xls"));  
                // 生成名为“sheet1”的工作表，参数0表示这是第一页  
                WritableSheet sheet = book.createSheet("sheet1", 0);  
                for (int i = 0; i<listClinic.size();i++) {
                	// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0),单元格内容为string  
                    Label label0 = new Label(0, i, listClinic.get(i).getStation());  
                    // 将定义好的单元格添加到工作表中  
                    sheet.addCell(label0);  
                    Label label1 = new Label(1, i, listClinic.get(i).getDate());  
                    sheet.addCell(label1);  
                    Label label2 = new Label(2, i, listClinic.get(i).getSO2());  
                    sheet.addCell(label2);  
                    Label label3 = new Label(3, i, listClinic.get(i).getNO2());  
                    sheet.addCell(label3);  
                    Label label4 = new Label(4, i, listClinic.get(i).getPM10());  
                    sheet.addCell(label4);  
                    Label label5 = new Label(5, i, listClinic.get(i).getCO());  
                    sheet.addCell(label5);  
                    Label label6 = new Label(6, i, listClinic.get(i).getO3());  
                    sheet.addCell(label6);  
                    Label label7 = new Label(7, i, listClinic.get(i).getPM25());  
                    sheet.addCell(label7);
                    Label label8 = new Label(8, i, listClinic.get(i).getPMc());  
                    sheet.addCell(label8);  
                    Label label9 = new Label(9, i, listClinic.get(i).getDiagnose());  
                    sheet.addCell(label9);  
                    Label label10 = new Label(10, i, listClinic.get(i).getICD_10());  
                    sheet.addCell(label10);  
                    Label label11 = new Label(11, i, listClinic.get(i).getDate_birth());  
                    sheet.addCell(label11);  
                    Label label12 = new Label(12, i, listClinic.get(i).getAge_0());  
                    sheet.addCell(label12);  
                    Label label13 = new Label(13, i, listClinic.get(i).getGender());  
                    sheet.addCell(label13);  
                    Label label14 = new Label(14, i, listClinic.get(i).getAge_1());  
                    sheet.addCell(label14);  
                    Label label15 = new Label(15, i, listClinic.get(i).getDate_discharge());  
                    sheet.addCell(label15);  
                    Label label16 = new Label(16, i, listClinic.get(i).getRegisted_address());  
                    sheet.addCell(label16);  
                    Label label17 = new Label(17, i, listClinic.get(i).getAdmission_cost());  
                    sheet.addCell(label17);  
                    Label label18 = new Label(18, i, listClinic.get(i).getAddress_present());  
                    sheet.addCell(label18);  
                    Label label19 = new Label(19, i, listClinic.get(i).getICD());  
                    sheet.addCell(label19);  
                    Label label20 = new Label(20, i, listClinic.get(i).getID());  
                    sheet.addCell(label20);  
				}
                
                // 写入数据并关闭文件  
                book.write();  
                book.close();  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }		
	}
	
	@RequestMapping("/welcome")
	public String welcome(ModelMap model, HttpServletRequest request, ServletResponse response) {
		return "welcome";
	}
}
