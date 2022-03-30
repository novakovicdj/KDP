package rs.ac.bg.etf.kdp.lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GoodsImpl implements Goods {

	ArrayList<String> body = new ArrayList<String>();
	String name;
	int it = 0;

	public GoodsImpl(String n) {
		this.name = n;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String[] getBody() {
		if (body != null) {
			return (String[]) this.body.toArray();
		} else
			return null;
	}

	@Override
	public void setBody(String[] body) {
		this.body = new ArrayList<>();
		for (int i = 0; i < body.length; i++) {
			this.body.add(body[i]);
		}
		it = 0;
	}

	@Override
	public String readLine() {
		if (it < this.body.size()) {
			return this.body.get(it++);
		} else {
			return null;
		}
	}

	@Override
	public void printLine(String body) {
		this.body.add(body);

	}

	@Override
	public int getNumLines() {
		if (this.body != null) {
			return this.body.size();
		} else {
			return -1;
		}
	}

	@Override
	public void save(String name) {
		try (FileOutputStream out = new FileOutputStream(new File(name));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));) {
			for (String s : this.body) {
				bw.write(s);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void load(String name) {
		try (FileInputStream in = new FileInputStream(new File(name));
				BufferedReader br = new BufferedReader(new InputStreamReader(in));) {
			String str;
			it = 0;
			this.body = new ArrayList<>();
			while ((str = br.readLine()) != null) {
				this.body.add(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
