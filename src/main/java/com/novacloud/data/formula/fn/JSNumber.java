package com.novacloud.data.formula.fn;

import com.novacloud.data.formula.Invocable;

import java.text.DecimalFormat;

class JSNumber extends JSObject implements Invocable {
	public Object toFixed(Number thiz, Object[] args) {
		int p = getIntArg(args, 0, 0);
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(p);
		df.setMaximumFractionDigits(p);
		df.setGroupingUsed(false);
		if(thiz.doubleValue() == 0.0){
			thiz = 0.0;
		}
		return df.format(thiz);
	}


	public Object toString(Number thiz, Object[] args) {
		int radix = getIntArg(args, 0, 10);
		return ECMA262Impl.toString(thiz,radix);
	}
}
