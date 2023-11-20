package com.gutengmorgen.TimeDial.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Model<E> {
	@Getter
	@Setter
	private int index = 0;
	private List<E> list;

	public Model() {
	}

	public Model(List<E> list) {
		this.list = list;
	}

	public void addAll(List<? extends E> c) {
		list.addAll(c);
	}

	public int getSize() {
		return list.size();
	}

	public void reduceIndex() {
		if (index > 0)
			index--;
	}

	public void increaseIndex() {
		if (list.size() > index + 1)
			index++;
	}

	public E getValueAt(int index) {
		return list.get(index);
	}

	public E getValue() {
		return list.get(index);
	}
}
