package cn.edu.ycm.union.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TaskTemplate {
	
	public boolean initializedFromExcel(String fileName){
		//初始化table
		rows = new ArrayList<>();
		//打开excel文件
		try( FileInputStream fis = new FileInputStream(fileName)){
			
			//新建一个excel对象
			XSSFWorkbook book = new XSSFWorkbook(fis);  
			//默认只读取第一个sheet中的模板
	        XSSFSheet sheet = book.getSheetAt(0);
	        name = sheet.getSheetName();
	        id = GenerateID.getID();
	        //取sheet迭代器
	        Iterator<Row> row = sheet.rowIterator();
			while(row.hasNext()){
				//新建一个缓冲row中用来存放cell的list
				List<TaskCell> taskCells = new ArrayList<TaskCell>();
				//aRow.setCells(taskCells);
				//取出行
				Row title = row.next();
				//取行迭代器
				Iterator<Cell> cellTitle = title.cellIterator(); 
				while (cellTitle.hasNext()) {  
					//取单元格
	                Cell cell = cellTitle.next();
	                //取单元格值
	                String value = null;
	                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
	                		value = Double.toString(cell.getNumericCellValue());
	                }else{
	                		value = cell.getStringCellValue(); 
	                }
	                value = value.trim();
	                //解析value，获得cell
	                TaskCell aTaskCell = getTaskCell(value);
	                taskCells.add(aTaskCell);
	            }  
				//新建一个缓冲row，将cell的List刷新到row中
				TaskRow tmpRow = new TaskRow();
				tmpRow.setCells(taskCells);
				rows.add(tmpRow);
			}
		} catch (Exception e) {
			System.out.print("excel模板解析出错:");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private TaskCell getTaskCell(String value){
		TaskCell taskCell = new TaskCell();
		if (value.contains(TaskCellType.NAME) //如果是变量类，设置类型type
				|| value.contains(TaskCellType.ID)
				||value.contains(TaskCellType.EDIT_NUMS)
				||value.contains(TaskCellType.EDIT_STRING)
				||value.contains(TaskCellType.SINGLE_SELECT)
				||value.contains(TaskCellType.MUTIPLY_SELECT)){//如果内容为姓名
			taskCell.setType(value);
		}else{
			taskCell.setValue(value);//如果是内容类，则直接赋值value
		}
		return taskCell;
	}
	
	private String id;
	private String name;
	List<TaskRow> rows;
	
	public String getId() {
		return id;
	}
	public List<TaskRow> getRows() {
		return rows;
	}

	public void setRows(List<TaskRow> rows) {
		this.rows = rows;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
