/**
 *
 */
// 创建一个闭包
(function($) {
	// 插件的定义
	$.jgrid = $.jgrid || {};
	$
			.extend(
					$.jgrid,
					{

						version : "4.6.0",
						htmlDecode : function(value) {
							if (value
									&& (value === '&nbsp;'
											|| value === '&#160;' || (value.length === 1 && value
											.charCodeAt(0) === 160))) {
								return "";
							}
							return !value ? value : String(value).replace(
									/&gt;/g, ">").replace(/&lt;/g, "<")
									.replace(/&quot;/g, '"').replace(/&amp;/g,
											"&");
						},
						htmlEncode : function(value) {
							return !value ? value : String(value).replace(/&/g,
									"&amp;").replace(/\"/g, "&quot;").replace(
									/</g, "&lt;").replace(/>/g, "&gt;");
						},
						format : function(format) { // jqgformat
							var args = $.makeArray(arguments).slice(1);
							if (format == null) {
								format = "";
							}
							return format.replace(/\{(\d+)\}/g, function(m, i) {
								return args[i];
							});
						},
						msie : navigator.appName === 'Microsoft Internet Explorer',
						msiever : function() {
							var rv = -1;
							var ua = navigator.userAgent;
							var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
							if (re.exec(ua) != null) {
								rv = parseFloat(RegExp.$1);
							}
							return rv;
						},
						getCellIndex : function(cell) {
							var c = $(cell);
							if (c.is('tr')) {
								return -1;
							}
							c = (!c.is('td') && !c.is('th') ? c
									.closest("td,th") : c)[0];
							if ($.jgrid.msie) {
								return $.inArray(c, c.parentNode.cells);
							}
							return c.cellIndex;
						},
						stripHtml : function(v) {
							v = String(v);
							var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
							if (v) {
								v = v.replace(regexp, "");
								return (v && v !== '&nbsp;' && v !== '&#160;') ? v
										.replace(/\"/g, "'")
										: "";
							}
							return v;
						},
						stripPref : function(pref, id) {
							var obj = $.type(pref);
							if (obj === "string" || obj === "number") {
								pref = String(pref);
								id = pref !== "" ? String(id).replace(
										String(pref), "") : id;
							}
							return id;
						},
						parse : function(jsonString) {
							var js = jsonString;
							if (js.substr(0, 9) === "while(1);") {
								js = js.substr(9);
							}
							if (js.substr(0, 2) === "/*") {
								js = js.substr(2, js.length - 4);
							}
							if (!js) {
								js = "{}";
							}
							return ($.jgrid.useJSON === true
									&& typeof JSON === 'object' && typeof JSON.parse === 'function') ? JSON
									.parse(js)
									: eval('(' + js + ')');
						},
						parseDate : function(format, date, newformat, opts) {
							var token = /\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g, timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g, timezoneClip = /[^-+\dA-Z]/g, msDateRegExp = new RegExp(
									"^\/Date\\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\\)\/$"), msMatch = ((typeof date === 'string') ? date
									.match(msDateRegExp)
									: null), pad = function(value, length) {
								value = String(value);
								length = parseInt(length, 10) || 2;
								while (value.length < length) {
									value = '0' + value;
								}
								return value;
							}, ts = {
								m : 1,
								d : 1,
								y : 1970,
								h : 0,
								i : 0,
								s : 0,
								u : 0
							}, timestamp = 0, dM, k, hl, h12to24 = function(
									ampm, h) {
								if (ampm === 0) {
									if (h === 12) {
										h = 0;
									}
								} else {
									if (h !== 12) {
										h += 12;
									}
								}
								return h;
							};
							if (opts === undefined) {
								opts = $.jgrid.formatter.date;
							}
							// old lang files
							if (opts.parseRe === undefined) {
								opts.parseRe = /[#%\\\/:_;.,\t\s-]/;
							}
							if (opts.masks.hasOwnProperty(format)) {
								format = opts.masks[format];
							}
							if (date && date != null) {
								if (!isNaN(date - 0)
										&& String(format).toLowerCase() === "u") {
									// Unix timestamp
									timestamp = new Date(
											parseFloat(date) * 1000);
								} else if (date.constructor === Date) {
									timestamp = date;
									// Microsoft date format support
								} else if (msMatch !== null) {
									timestamp = new Date(parseInt(msMatch[1],
											10));
									if (msMatch[3]) {
										var offset = Number(msMatch[5]) * 60
												+ Number(msMatch[6]);
										offset *= ((msMatch[4] === '-') ? 1
												: -1);
										offset -= timestamp.getTimezoneOffset();
										timestamp
												.setTime(Number(Number(timestamp)
														+ (offset * 60 * 1000)));
									}
								} else {
									var offset = 0;
									// Support ISO8601Long that have Z at the
									// end to indicate UTC timezone
									if (opts.srcformat === 'ISO8601Long'
											&& date.charAt(date.length - 1) === 'Z') {
										offset -= (new Date())
												.getTimezoneOffset();
									}
									date = String(date).replace(/\T/g, "#")
											.replace(/\t/, "%").split(
													opts.parseRe);
									format = format.replace(/\T/g, "#")
											.replace(/\t/, "%").split(
													opts.parseRe);
									// parsing for month names
									for (k = 0, hl = format.length; k < hl; k++) {
										if (format[k] === 'M') {
											dM = $.inArray(date[k],
													opts.monthNames);
											if (dM !== -1 && dM < 12) {
												date[k] = dM + 1;
												ts.m = date[k];
											}
										}
										if (format[k] === 'F') {
											dM = $.inArray(date[k],
													opts.monthNames, 12);
											if (dM !== -1 && dM > 11) {
												date[k] = dM + 1 - 12;
												ts.m = date[k];
											}
										}
										if (format[k] === 'a') {
											dM = $.inArray(date[k], opts.AmPm);
											if (dM !== -1
													&& dM < 2
													&& date[k] === opts.AmPm[dM]) {
												date[k] = dM;
												ts.h = h12to24(date[k], ts.h);
											}
										}
										if (format[k] === 'A') {
											dM = $.inArray(date[k], opts.AmPm);
											if (dM !== -1
													&& dM > 1
													&& date[k] === opts.AmPm[dM]) {
												date[k] = dM - 2;
												ts.h = h12to24(date[k], ts.h);
											}
										}
										if (format[k] === 'g') {
											ts.h = parseInt(date[k], 10);
										}
										if (date[k] !== undefined) {
											ts[format[k].toLowerCase()] = parseInt(
													date[k], 10);
										}
									}
									if (ts.f) {
										ts.m = ts.f;
									}
									if (ts.m === 0 && ts.y === 0 && ts.d === 0) {
										return "&#160;";
									}
									ts.m = parseInt(ts.m, 10) - 1;
									var ty = ts.y;
									if (ty >= 70 && ty <= 99) {
										ts.y = 1900 + ts.y;
									} else if (ty >= 0 && ty <= 69) {
										ts.y = 2000 + ts.y;
									}
									timestamp = new Date(ts.y, ts.m, ts.d,
											ts.h, ts.i, ts.s, ts.u);
									// Apply offset to show date as local time.
									if (offset > 0) {
										timestamp
												.setTime(Number(Number(timestamp)
														+ (offset * 60 * 1000)));
									}
								}
							} else {
								timestamp = new Date(ts.y, ts.m, ts.d, ts.h,
										ts.i, ts.s, ts.u);
							}
							if (newformat === undefined) {
								return timestamp;
							}
							if (opts.masks.hasOwnProperty(newformat)) {
								newformat = opts.masks[newformat];
							} else if (!newformat) {
								newformat = 'Y-m-d';
							}
							var G = timestamp.getHours(), i = timestamp
									.getMinutes(), j = timestamp.getDate(), n = timestamp
									.getMonth() + 1, o = timestamp
									.getTimezoneOffset(), s = timestamp
									.getSeconds(), u = timestamp
									.getMilliseconds(), w = timestamp.getDay(), Y = timestamp
									.getFullYear(), N = (w + 6) % 7 + 1, z = (new Date(
									Y, n - 1, j) - new Date(Y, 0, 1)) / 86400000, flags = {
								// Day
								d : pad(j),
								D : opts.dayNames[w],
								j : j,
								l : opts.dayNames[w + 7],
								N : N,
								S : opts.S(j),
								// j < 11 || j > 13 ? ['st', 'nd', 'rd',
								// 'th'][Math.min((j - 1) % 10, 3)] : 'th',
								w : w,
								z : z,
								// Week
								W : N < 5 ? Math.floor((z + N - 1) / 7) + 1
										: Math.floor((z + N - 1) / 7)
												|| ((new Date(Y - 1, 0, 1)
														.getDay() + 6) % 7 < 4 ? 53
														: 52),
								// Month
								F : opts.monthNames[n - 1 + 12],
								m : pad(n),
								M : opts.monthNames[n - 1],
								n : n,
								t : '?',
								// Year
								L : '?',
								o : '?',
								Y : Y,
								y : String(Y).substring(2),
								// Time
								a : G < 12 ? opts.AmPm[0] : opts.AmPm[1],
								A : G < 12 ? opts.AmPm[2] : opts.AmPm[3],
								B : '?',
								g : G % 12 || 12,
								G : G,
								h : pad(G % 12 || 12),
								H : pad(G),
								i : pad(i),
								s : pad(s),
								u : u,
								// Timezone
								e : '?',
								I : '?',
								O : (o > 0 ? "-" : "+")
										+ pad(Math.floor(Math.abs(o) / 60)
												* 100 + Math.abs(o) % 60, 4),
								P : '?',
								T : (String(timestamp).match(timezone) || [ "" ])
										.pop().replace(timezoneClip, ""),
								Z : '?',
								// Full Date/Time
								c : '?',
								r : '?',
								U : Math.floor(timestamp / 1000)
							};
							return newformat.replace(token, function($0) {
								return flags.hasOwnProperty($0) ? flags[$0]
										: $0.substring(1);
							});
						},
						jqID : function(sid) {
							return String(sid).replace(
									/[!"#$%&'()*+,.\/:; <=>?@\[\\\]\^`{|}~]/g,
									"\\$&");
						},
						guid : 1,
						uidPref : 'jqg',
						randId : function(prefix) {
							return (prefix || $.jgrid.uidPref)
									+ ($.jgrid.guid++);
						},
						getAccessor : function(obj, expr) {
							var ret, p, prm = [], i;
							if (typeof expr === 'function') {
								return expr(obj);
							}
							ret = obj[expr];
							if (ret === undefined) {
								try {
									if (typeof expr === 'string') {
										prm = expr.split('.');
									}
									i = prm.length;
									if (i) {
										ret = obj;
										while (ret && i--) {
											p = prm.shift();
											ret = ret[p];
										}
									}
								} catch (e) {
								}
							}
							return ret;
						},
						getXmlData : function(obj, expr, returnObj) {
							var ret, m = typeof expr === 'string' ? expr
									.match(/^(.*)\[(\w+)\]$/) : null;
							if (typeof expr === 'function') {
								return expr(obj);
							}
							if (m && m[2]) {
								// m[2] is the attribute selector
								// m[1] is an optional element selector
								// examples: "[id]", "rows[page]"
								return m[1] ? $(m[1], obj).attr(m[2]) : $(obj)
										.attr(m[2]);
							}
							ret = $(expr, obj);
							if (returnObj) {
								return ret;
							}
							// $(expr, obj).filter(':last'); // we use ':last'
							// to be more compatible with old version of jqGrid
							return ret.length > 0 ? $(ret).text() : undefined;
						},
						cellWidth : function() {
							var $testDiv = $("<div class='ui-jqGrid' style='left:10000px'><table class='ui-jqGrid-btable' style='width:5px;'><tr class='jqgrow'><td style='width:5px;display:block;'></td></tr></table></div>"), testCell = $testDiv
									.appendTo("body").find("td").width();
							$testDiv.remove();
							return Math.abs(testCell - 5) > 0.1;
						},
						cell_width : true,
						ajaxOptions : {},
						from : function(source) {
							// Original Author Hugo Bonacci
							// License MIT http://jlinq.codeplex.com/license
							var QueryObject = function(d, q) {
								if (typeof d === "string") {
									d = $.data(d);
								}
								var self = this, _data = d, _usecase = true, _trim = false, _query = q, _stripNum = /[\$,%]/g, _lastCommand = null, _lastField = null, _orDepth = 0, _negate = false, _queuedOperator = "", _sorting = [], _useProperties = true;
								if (typeof d === "object" && d.push) {
									if (d.length > 0) {
										if (typeof d[0] !== "object") {
											_useProperties = false;
										} else {
											_useProperties = true;
										}
									}
								} else {
									throw "data provides is not an array";
								}
								this._hasData = function() {
									return _data === null ? false
											: _data.length === 0 ? false : true;
								};
								this._getStr = function(s) {
									var phrase = [];
									if (_trim) {
										phrase.push("jQuery.trim(");
									}
									phrase.push("String(" + s + ")");
									if (_trim) {
										phrase.push(")");
									}
									if (!_usecase) {
										phrase.push(".toLowerCase()");
									}
									return phrase.join("");
								};
								this._strComp = function(val) {
									if (typeof val === "string") {
										return ".toString()";
									}
									return "";
								};
								this._group = function(f, u) {
									return ({
										field : f.toString(),
										unique : u,
										items : []
									});
								};
								this._toStr = function(phrase) {
									if (_trim) {
										phrase = $.trim(phrase);
									}
									phrase = phrase.toString().replace(/\\/g,
											'\\\\').replace(/\"/g, '\\"');
									return _usecase ? phrase : phrase
											.toLowerCase();
								};
								this._funcLoop = function(func) {
									var results = [];
									$.each(_data, function(i, v) {
										results.push(func(v));
									});
									return results;
								};
								this._append = function(s) {
									var i;
									if (_query === null) {
										_query = "";
									} else {
										_query += _queuedOperator === "" ? " && "
												: _queuedOperator;
									}
									for (i = 0; i < _orDepth; i++) {
										_query += "(";
									}
									if (_negate) {
										_query += "!";
									}
									_query += "(" + s + ")";
									_negate = false;
									_queuedOperator = "";
									_orDepth = 0;
								};
								this._setCommand = function(f, c) {
									_lastCommand = f;
									_lastField = c;
								};
								this._resetNegate = function() {
									_negate = false;
								};
								this._repeatCommand = function(f, v) {
									if (_lastCommand === null) {
										return self;
									}
									if (f !== null && v !== null) {
										return _lastCommand(f, v);
									}
									if (_lastField === null) {
										return _lastCommand(f);
									}
									if (!_useProperties) {
										return _lastCommand(f);
									}
									return _lastCommand(_lastField, f);
								};
								this._equals = function(a, b) {
									return (self._compare(a, b, 1) === 0);
								};
								this._compare = function(a, b, d) {
									var toString = Object.prototype.toString;
									if (d === undefined) {
										d = 1;
									}
									if (a === undefined) {
										a = null;
									}
									if (b === undefined) {
										b = null;
									}
									if (a === null && b === null) {
										return 0;
									}
									if (a === null && b !== null) {
										return 1;
									}
									if (a !== null && b === null) {
										return -1;
									}
									if (toString.call(a) === '[object Date]'
											&& toString.call(b) === '[object Date]') {
										if (a < b) {
											return -d;
										}
										if (a > b) {
											return d;
										}
										return 0;
									}
									if (!_usecase && typeof a !== "number"
											&& typeof b !== "number") {
										a = String(a);
										b = String(b);
									}
									if (a < b) {
										return -d;
									}
									if (a > b) {
										return d;
									}
									return 0;
								};
								this._performSort = function() {
									if (_sorting.length === 0) {
										return;
									}
									_data = self._doSort(_data, 0);
								};
								this._doSort = function(d, q) {
									var by = _sorting[q].by, dir = _sorting[q].dir, type = _sorting[q].type, dfmt = _sorting[q].datefmt, sfunc = _sorting[q].sfunc;
									if (q === _sorting.length - 1) {
										return self._getOrder(d, by, dir, type,
												dfmt, sfunc);
									}
									q++;
									var values = self._getGroup(d, by, dir,
											type, dfmt), results = [], i, j, sorted;
									for (i = 0; i < values.length; i++) {
										sorted = self._doSort(values[i].items,
												q);
										for (j = 0; j < sorted.length; j++) {
											results.push(sorted[j]);
										}
									}
									return results;
								};
								this._getOrder = function(data, by, dir, type,
										dfmt, sfunc) {
									var sortData = [], _sortData = [], newDir = dir === "a" ? 1
											: -1, i, ab, j, findSortKey;

									if (type === undefined) {
										type = "text";
									}
									if (type === 'float' || type === 'number'
											|| type === 'currency'
											|| type === 'numeric') {
										findSortKey = function($cell) {
											var key = parseFloat(String($cell)
													.replace(_stripNum, ''));
											return isNaN(key) ? Number.NEGATIVE_INFINITY
													: key;
										};
									} else if (type === 'int'
											|| type === 'integer') {
										findSortKey = function($cell) {
											return $cell ? parseFloat(String(
													$cell).replace(_stripNum,
													''))
													: Number.NEGATIVE_INFINITY;
										};
									} else if (type === 'date'
											|| type === 'datetime') {
										findSortKey = function($cell) {
											return $.jgrid.parseDate(dfmt,
													$cell).getTime();
										};
									} else if ($.isFunction(type)) {
										findSortKey = type;
									} else {
										findSortKey = function($cell) {
											$cell = $cell ? $
													.trim(String($cell)) : "";
											return _usecase ? $cell : $cell
													.toLowerCase();
										};
									}
									$.each(data, function(i, v) {
										ab = by !== "" ? $.jgrid.getAccessor(v,
												by) : v;
										if (ab === undefined) {
											ab = "";
										}
										ab = findSortKey(ab, v);
										_sortData.push({
											'vSort' : ab,
											'index' : i
										});
									});
									if ($.isFunction(sfunc)) {
										_sortData.sort(function(a, b) {
											a = a.vSort;
											b = b.vSort;
											return sfunc.call(this, a, b,
													newDir);
										});
									} else {
										_sortData.sort(function(a, b) {
											a = a.vSort;
											b = b.vSort;
											return self._compare(a, b, newDir);
										});
									}
									j = 0;
									var nrec = data.length;
									// overhead, but we do not change the
									// original data.
									while (j < nrec) {
										i = _sortData[j].index;
										sortData.push(data[i]);
										j++;
									}
									return sortData;
								};
								this._getGroup = function(data, by, dir, type,
										dfmt) {
									var results = [], group = null, last = null, val;
									$.each(self._getOrder(data, by, dir, type,
											dfmt), function(i, v) {
										val = $.jgrid.getAccessor(v, by);
										if (val == null) {
											val = "";
										}
										if (!self._equals(last, val)) {
											last = val;
											if (group !== null) {
												results.push(group);
											}
											group = self._group(by, val);
										}
										group.items.push(v);
									});
									if (group !== null) {
										results.push(group);
									}
									return results;
								};
								this.ignoreCase = function() {
									_usecase = false;
									return self;
								};
								this.useCase = function() {
									_usecase = true;
									return self;
								};
								this.trim = function() {
									_trim = true;
									return self;
								};
								this.noTrim = function() {
									_trim = false;
									return self;
								};
								this.execute = function() {
									var match = _query, results = [];
									if (match === null) {
										return self;
									}
									$.each(_data, function() {
										if (eval(match)) {
											results.push(this);
										}
									});
									_data = results;
									return self;
								};
								this.data = function() {
									return _data;
								};
								this.select = function(f) {
									self._performSort();
									if (!self._hasData()) {
										return [];
									}
									self.execute();
									if ($.isFunction(f)) {
										var results = [];
										$.each(_data, function(i, v) {
											results.push(f(v));
										});
										return results;
									}
									return _data;
								};
								this.hasMatch = function() {
									if (!self._hasData()) {
										return false;
									}
									self.execute();
									return _data.length > 0;
								};
								this.andNot = function(f, v, x) {
									_negate = !_negate;
									return self.and(f, v, x);
								};
								this.orNot = function(f, v, x) {
									_negate = !_negate;
									return self.or(f, v, x);
								};
								this.not = function(f, v, x) {
									return self.andNot(f, v, x);
								};
								this.and = function(f, v, x) {
									_queuedOperator = " && ";
									if (f === undefined) {
										return self;
									}
									return self._repeatCommand(f, v, x);
								};
								this.or = function(f, v, x) {
									_queuedOperator = " || ";
									if (f === undefined) {
										return self;
									}
									return self._repeatCommand(f, v, x);
								};
								this.orBegin = function() {
									_orDepth++;
									return self;
								};
								this.orEnd = function() {
									if (_query !== null) {
										_query += ")";
									}
									return self;
								};
								this.isNot = function(f) {
									_negate = !_negate;
									return self.is(f);
								};
								this.is = function(f) {
									self._append('this.' + f);
									self._resetNegate();
									return self;
								};
								this._compareValues = function(func, f, v, how,
										t) {
									var fld;
									if (_useProperties) {
										fld = 'jQuery.jgrid.getAccessor(this,\''
												+ f + '\')';
									} else {
										fld = 'this';
									}
									if (v === undefined) {
										v = null;
									}
									// var val=v===null?f:v,
									var val = v, swst = t.stype === undefined ? "text"
											: t.stype;
									if (v !== null) {
										switch (swst) {
										case 'int':
										case 'integer':
											val = (isNaN(Number(val)) || val === "") ? '0'
													: val; // To be fixed with
															// more inteligent
															// code
											fld = 'parseInt(' + fld + ',10)';
											val = 'parseInt(' + val + ',10)';
											break;
										case 'float':
										case 'number':
										case 'numeric':
											val = String(val).replace(
													_stripNum, '');
											val = (isNaN(Number(val)) || val === "") ? '0'
													: val; // To be fixed with
															// more inteligent
															// code
											fld = 'parseFloat(' + fld + ')';
											val = 'parseFloat(' + val + ')';
											break;
										case 'date':
										case 'datetime':
											val = String($.jgrid.parseDate(
													t.newfmt || 'Y-m-d', val)
													.getTime());
											fld = 'jQuery.jgrid.parseDate("'
													+ t.srcfmt + '",' + fld
													+ ').getTime()';
											break;
										default:
											fld = self._getStr(fld);
											val = self._getStr('"'
													+ self._toStr(val) + '"');
										}
									}
									self._append(fld + ' ' + how + ' ' + val);
									self._setCommand(func, f);
									self._resetNegate();
									return self;
								};
								this.equals = function(f, v, t) {
									return self._compareValues(self.equals, f,
											v, "==", t);
								};
								this.notEquals = function(f, v, t) {
									return self._compareValues(self.equals, f,
											v, "!==", t);
								};
								this.isNull = function(f, v, t) {
									return self._compareValues(self.equals, f,
											null, "===", t);
								};
								this.greater = function(f, v, t) {
									return self._compareValues(self.greater, f,
											v, ">", t);
								};
								this.less = function(f, v, t) {
									return self._compareValues(self.less, f, v,
											"<", t);
								};
								this.greaterOrEquals = function(f, v, t) {
									return self
											._compareValues(
													self.greaterOrEquals, f, v,
													">=", t);
								};
								this.lessOrEquals = function(f, v, t) {
									return self._compareValues(
											self.lessOrEquals, f, v, "<=", t);
								};
								this.startsWith = function(f, v) {
									var val = (v == null) ? f : v, length = _trim ? $
											.trim(val.toString()).length
											: val.toString().length;
									if (_useProperties) {
										self
												._append(self
														._getStr('jQuery.jgrid.getAccessor(this,\''
																+ f + '\')')
														+ '.substr(0,'
														+ length
														+ ') == '
														+ self
																._getStr('"'
																		+ self
																				._toStr(v)
																		+ '"'));
									} else {
										if (v != null) {
											length = _trim ? $.trim(v
													.toString()).length : v
													.toString().length;
										}
										self
												._append(self._getStr('this')
														+ '.substr(0,'
														+ length
														+ ') == '
														+ self
																._getStr('"'
																		+ self
																				._toStr(f)
																		+ '"'));
									}
									self._setCommand(self.startsWith, f);
									self._resetNegate();
									return self;
								};
								this.endsWith = function(f, v) {
									var val = (v == null) ? f : v, length = _trim ? $
											.trim(val.toString()).length
											: val.toString().length;
									if (_useProperties) {
										self
												._append(self
														._getStr('jQuery.jgrid.getAccessor(this,\''
																+ f + '\')')
														+ '.substr('
														+ self
																._getStr('jQuery.jgrid.getAccessor(this,\''
																		+ f
																		+ '\')')
														+ '.length-'
														+ length
														+ ','
														+ length
														+ ') == "'
														+ self._toStr(v) + '"');
									} else {
										self._append(self._getStr('this')
												+ '.substr('
												+ self._getStr('this')
												+ '.length-"' + self._toStr(f)
												+ '".length,"' + self._toStr(f)
												+ '".length) == "'
												+ self._toStr(f) + '"');
									}
									self._setCommand(self.endsWith, f);
									self._resetNegate();
									return self;
								};
								this.contains = function(f, v) {
									if (_useProperties) {
										self
												._append(self
														._getStr('jQuery.jgrid.getAccessor(this,\''
																+ f + '\')')
														+ '.indexOf("'
														+ self._toStr(v)
														+ '",0) > -1');
									} else {
										self._append(self._getStr('this')
												+ '.indexOf("' + self._toStr(f)
												+ '",0) > -1');
									}
									self._setCommand(self.contains, f);
									self._resetNegate();
									return self;
								};
								this.groupBy = function(by, dir, type, datefmt) {
									if (!self._hasData()) {
										return null;
									}
									return self._getGroup(_data, by, dir, type,
											datefmt);
								};
								this.orderBy = function(by, dir, stype, dfmt,
										sfunc) {
									dir = dir == null ? "a" : $.trim(dir
											.toString().toLowerCase());
									if (stype == null) {
										stype = "text";
									}
									if (dfmt == null) {
										dfmt = "Y-m-d";
									}
									if (sfunc == null) {
										sfunc = false;
									}
									if (dir === "desc" || dir === "descending") {
										dir = "d";
									}
									if (dir === "asc" || dir === "ascending") {
										dir = "a";
									}
									_sorting.push({
										by : by,
										dir : dir,
										type : stype,
										datefmt : dfmt,
										sfunc : sfunc
									});
									return self;
								};
								return self;
							};
							return new QueryObject(source, null);
						},
						getMethod : function(name) {
							return this.getAccessor($.fn.jqGrid, name);
						},
						extend : function(methods) {
							$.extend($.fn.jqGrid, methods);
							if (!this.no_legacy_api) {
								$.fn.extend(methods);
							}
						}

					});

	$.fn.jqGrid = function(pin) {
		if (typeof pin == 'string') {
			// return $(this).data('grid');
			// var grid = $(this).data('grid');
			// this.grid =grid;
			// if(grid==null)
			// logger.error("jqGrid need param this element has't init grid");
			// else
			// return grid;
			var fn = $.jgrid.getMethod(pin);
			if (!fn) {
				throw ("jqGrid - No such method: " + pin);
			}
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this, args);
		}
		/*
		 * this.defaults={}; if (typeof pin === 'string') {
		 * this.grid=$(this).data("grid"); //alert($(this).data("grid"));
		 * alert(this.grid); var fn = $.fn.jqGrid.getMethod(pin); if (!fn) {
		 * throw ("jqGrid - No such method: " + pin); } var args =
		 * $.makeArray(arguments).slice(1); return fn.apply(this,args); }
		 */

		return this.each(function() {
			if (this.grid) {
				return;
			}
			var p = $.extend(true, {
				url : "",
				height : 150,
				page : 1,
				rowNum : 20,
				rowTotal : null,
				records : 0,
				pager : "",
				pgbuttons : true,
				pginput : true,
				colModel : [],
				rowList : [],
				colNames : [],
				sortorder : "asc",
				sortname : "",
				datatype : "xml",
				mtype : "GET",
				altRows : false,
				selarrrow : [],
				savedRow : [],
				shrinkToFit : true,
				xmlReader : {},
				jsonReader : {},
				subGrid : false,
				subGridModel : [],
				reccount : 0,
				lastpage : 0,
				lastsort : 0,
				selrow : null,
				beforeSelectRow : null,
				onSelectRow : null,
				onSortCol : null,
				ondblClickRow : null,
				onRightClickRow : null,
				onPaging : null,
				onSelectAll : null,
				onInitGrid : null,
				loadComplete : null,
				gridComplete : null,
				loadError : null,
				loadBeforeSend : null,
				afterInsertRow : null,
				beforeRequest : null,
				beforeProcessing : null,
				onHeaderClick : null,
				viewrecords : false,
				loadonce : false,
				multiselect : false,
				multikey : false,
				editurl : null,
				search : false,
				caption : "",
				hidegrid : true,
				hiddengrid : false,
				postData : {},
				userData : {},
				treeGrid : false,
				treeGridModel : 'nested',
				treeReader : {},
				treeANode : -1,
				ExpandColumn : null,
				tree_root_level : 0,
				prmNames : {
					page : "page",
					rows : "rows",
					sort : "sidx",
					order : "sord",
					search : "_search",
					nd : "nd",
					id : "id",
					oper : "oper",
					editoper : "edit",
					addoper : "add",
					deloper : "del",
					subgridid : "id",
					npage : null,
					totalrows : "totalrows"
				},
				forceFit : false,
				gridstate : "visible",
				cellEdit : false,
				cellsubmit : "remote",
				nv : 0,
				loadui : "enable",
				toolbar : [ false, "" ],
				scroll : false,
				multiboxonly : false,
				deselectAfterSort : true,
				scrollrows : false,
				autowidth : false,
				scrollOffset : 18,
				cellLayout : 5,
				subGridWidth : 20,
				multiselectWidth : 20,
				gridview : false,
				rownumWidth : 25,
				rownumbers : false,
				pagerpos : 'center',
				recordpos : 'right',
				footerrow : false,
				userDataOnFooter : false,
				hoverrows : true,
				altclass : 'ui-priority-secondary',
				viewsortcols : [ false, 'vertical', true ],
				resizeclass : '',
				autoencode : false,
				remapColumns : [],
				ajaxGridOptions : {},
				direction : "ltr",
				toppager : false,
				headertitles : false,
				scrollTimeout : 40,
				data : [],
				_index : {},
				grouping : false,
				groupingView : {
					groupField : [],
					groupOrder : [],
					groupText : [],
					groupColumnShow : [],
					groupSummary : [],
					showSummaryOnHide : false,
					sortitems : [],
					sortnames : [],
					summary : [],
					summaryval : [],
					plusicon : 'ui-icon-circlesmall-plus',
					minusicon : 'ui-icon-circlesmall-minus',
					displayField : [],
					groupSummaryPos : [],
					formatDisplayField : [],
					_locgr : false
				},
				ignoreCase : false,
				cmTemplate : {},
				idPrefix : "",
				multiSort : false,
				minColWidth : 33
			}, $.fn.jqGrid.defaults, pin || {});

			var ts = this, grid = {
				width_sum : 0,
			/*
			 * curPage:1, pageSize:15, totalPage:1,
			 */
			/*
			 * width_sum:0,
			 *
			 * multiselect:false, url:'', grid_selector:"", pager_selector:"",
			 * searchParams:{}, colNames:[], colModel:[], loadComplete:null,
			 * rowNum:0, selrow:null, data:null,
			 * page:{curPage:1,pageSize:15,totalPage:0,totalcount:0},
			 */

			};

			$(this).empty().attr("tabindex", "0");
			this.p = p;

			// $.extends();

			// $(ts).data("grid",grid);
			// this.grid=grid;
			$(ts).jqGrid("initGrid");
			// return grid;
		});
	};

	$.jgrid
			.extend({
				getGridParam : function(pName) {
					var $t = this[0];
					if (!$t || !$t.grid) {
						return;
					}
					if (!pName) {
						return $t.p;
					}
					return $t.p[pName] !== undefined ? $t.p[pName] : null;
				},
				setGridParam : function(newParams) {
					return this.each(function() {
						if (this.grid && typeof newParams === 'object') {
							$.extend(true, this.p, newParams);
						}
					});
				},
				getGridRowById : function(rowid) {
					var row;
					this.each(function() {
						try {
							// row = this.rows.namedItem( rowid );
							var i = this.rows.length;
							while (i--) {
								if (rowid.toString() === this.rows[i].id) {
									row = this.rows[i];
									break;
								}
							}
						} catch (e) {
							row = $(this.grid.bDiv).find(
									"#" + $.jgrid.jqID(rowid));
						}
					});
					return row;
				},
				getDataIDs : function() {
					var ids = [], i = 0, len, j = 0;
					this.each(function() {
						len = this.rows.length;
						if (len && len > 0) {
							while (i < len) {
								if ($(this.rows[i]).hasClass('jqgrow')) {
									ids[j] = this.rows[i].id;
									j++;
								}
								i++;
							}
						}
					});
					return ids;
				},
				setSelection : function(selection, onsr, e) {
					return this
							.each(function() {
								var $t = this, stat, pt, ner, ia, tpsr, fid, csr;
								if (selection === undefined) {
									return;
								}
								onsr = onsr === false ? false : true;
								pt = $($t).jqGrid('getGridRowById', selection);
								if (!pt
										|| !pt.className
										|| pt.className
												.indexOf('ui-state-disabled') > -1) {
									return;
								}
								function scrGrid(iR) {
									var ch = $($t.grid.bDiv)[0].clientHeight, st = $($t.grid.bDiv)[0].scrollTop, rpos = $(
											$t.rows[iR]).position().top, rh = $t.rows[iR].clientHeight;
									if (rpos + rh >= ch + st) {
										$($t.grid.bDiv)[0].scrollTop = rpos
												- (ch + st) + rh + st;
									} else if (rpos < ch + st) {
										if (rpos < st) {
											$($t.grid.bDiv)[0].scrollTop = rpos;
										}
									}
								}
								if ($t.p.scrollrows === true) {
									ner = $($t).jqGrid('getGridRowById',
											selection).rowIndex;
									if (ner >= 0) {
										scrGrid(ner);
									}
								}
								if ($t.p.frozenColumns === true) {
									fid = $t.p.id + "_frozen";
								}
								if (!$t.p.multiselect) {
									if (pt.className !== "ui-subgrid") {
										if ($t.p.selrow !== pt.id) {
											csr = $($t).jqGrid(
													'getGridRowById',
													$t.p.selrow);
											if (csr) {
												$(csr)
														.removeClass(
																"ui-state-highlight")
														.attr(
																{
																	"aria-selected" : "false",
																	"tabindex" : "-1"
																});
											}
											$(pt)
													.addClass(
															"ui-state-highlight")
													.attr(
															{
																"aria-selected" : "true",
																"tabindex" : "0"
															});// .focus();
											if (fid) {
												$(
														"#"
																+ $.jgrid
																		.jqID($t.p.selrow),
														"#" + $.jgrid.jqID(fid))
														.removeClass(
																"ui-state-highlight");
												$(
														"#"
																+ $.jgrid
																		.jqID(selection),
														"#" + $.jgrid.jqID(fid))
														.addClass(
																"ui-state-highlight");
											}
											stat = true;
										} else {
											stat = false;
										}
										$t.p.selrow = pt.id;
										if (onsr) {
											$($t).triggerHandler(
													"jqGridSelectRow",
													[ pt.id, stat, e ]);
											if ($t.p.onSelectRow) {
												$t.p.onSelectRow.call($t,
														pt.id, stat, e);
											}
										}
									}
								} else {
									// unselect selectall checkbox when
									// deselecting a specific row
									$t.setHeadCheckBox(false);
									$t.p.selrow = pt.id;
									ia = $.inArray($t.p.selrow, $t.p.selarrrow);
									if (ia === -1) {
										if (pt.className !== "ui-subgrid") {
											$(pt)
													.addClass(
															"ui-state-highlight")
													.attr("aria-selected",
															"true");
										}
										stat = true;
										$t.p.selarrrow.push($t.p.selrow);
									} else {
										if (pt.className !== "ui-subgrid") {
											$(pt).removeClass(
													"ui-state-highlight").attr(
													"aria-selected", "false");
										}
										stat = false;
										$t.p.selarrrow.splice(ia, 1);
										tpsr = $t.p.selarrrow[0];
										$t.p.selrow = (tpsr === undefined) ? null
												: tpsr;
									}
									$("#jqg_" + $.jgrid.jqID($t.p.id) + "_"
											+ $.jgrid.jqID(pt.id))[$t.p.useProp ? 'prop'
											: 'attr']("checked", stat);
									if (fid) {
										if (ia === -1) {
											$("#" + $.jgrid.jqID(selection),
													"#" + $.jgrid.jqID(fid))
													.addClass(
															"ui-state-highlight");
										} else {
											$("#" + $.jgrid.jqID(selection),
													"#" + $.jgrid.jqID(fid))
													.removeClass(
															"ui-state-highlight");
										}
										$("#jqg_" + $.jgrid.jqID($t.p.id) + "_"
												+ $.jgrid.jqID(selection), "#"
												+ $.jgrid.jqID(fid))[$t.p.useProp ? 'prop'
												: 'attr']("checked", stat);
									}
									if (onsr) {
										$($t).triggerHandler("jqGridSelectRow",
												[ pt.id, stat, e ]);
										if ($t.p.onSelectRow) {
											$t.p.onSelectRow.call($t, pt.id,
													stat, e);
										}
									}
								}
							});
				},
				resetSelection : function(rowid) {
					return this
							.each(function() {
								var t = this, sr, fid;
								if (t.p.frozenColumns === true) {
									fid = t.p.id + "_frozen";
								}
								if (rowid !== undefined) {
									sr = rowid === t.p.selrow ? t.p.selrow
											: rowid;
									$(
											"#" + $.jgrid.jqID(t.p.id)
													+ " tbody:first tr#"
													+ $.jgrid.jqID(sr))
											.removeClass("ui-state-highlight")
											.attr("aria-selected", "false");
									if (fid) {
										$("#" + $.jgrid.jqID(sr),
												"#" + $.jgrid.jqID(fid))
												.removeClass(
														"ui-state-highlight");
									}
									if (t.p.multiselect) {
										$("#jqg_" + $.jgrid.jqID(t.p.id) + "_"
												+ $.jgrid.jqID(sr), "#"
												+ $.jgrid.jqID(t.p.id))[t.p.useProp ? 'prop'
												: 'attr']("checked", false);
										if (fid) {
											$("#jqg_" + $.jgrid.jqID(t.p.id)
													+ "_" + $.jgrid.jqID(sr),
													"#" + $.jgrid.jqID(fid))[t.p.useProp ? 'prop'
													: 'attr']("checked", false);
										}
										t.setHeadCheckBox(false);
									}
									sr = null;
								} else if (!t.p.multiselect) {
									if (t.p.selrow) {
										$(
												"#"
														+ $.jgrid.jqID(t.p.id)
														+ " tbody:first tr#"
														+ $.jgrid
																.jqID(t.p.selrow))
												.removeClass(
														"ui-state-highlight")
												.attr("aria-selected", "false");
										if (fid) {
											$("#" + $.jgrid.jqID(t.p.selrow),
													"#" + $.jgrid.jqID(fid))
													.removeClass(
															"ui-state-highlight");
										}
										t.p.selrow = null;
									}
								} else {
									$(t.p.selarrrow)
											.each(
													function(i, n) {
														$(
																$(t)
																		.jqGrid(
																				'getGridRowById',
																				n))
																.removeClass(
																		"ui-state-highlight")
																.attr(
																		"aria-selected",
																		"false");
														$("#jqg_"
																+ $.jgrid
																		.jqID(t.p.id)
																+ "_"
																+ $.jgrid
																		.jqID(n))[t.p.useProp ? 'prop'
																: 'attr'](
																"checked",
																false);
														if (fid) {
															$(
																	"#"
																			+ $.jgrid
																					.jqID(n),
																	"#"
																			+ $.jgrid
																					.jqID(fid))
																	.removeClass(
																			"ui-state-highlight");
															$(
																	"#jqg_"
																			+ $.jgrid
																					.jqID(t.p.id)
																			+ "_"
																			+ $.jgrid
																					.jqID(n),
																	"#"
																			+ $.jgrid
																					.jqID(fid))[t.p.useProp ? 'prop'
																	: 'attr'](
																	"checked",
																	false);
														}
													});
									t.setHeadCheckBox(false);
									t.p.selarrrow = [];
									t.p.selrow = null;
								}
								if (t.p.cellEdit === true) {
									if (parseInt(t.p.iCol, 10) >= 0
											&& parseInt(t.p.iRow, 10) >= 0) {
										$("td:eq(" + t.p.iCol + ")",
												t.rows[t.p.iRow]).removeClass(
												"edit-cell ui-state-highlight");
										$(t.rows[t.p.iRow]).removeClass(
												"selected-row ui-state-hover");
									}
								}
								t.p.savedRow = [];
							});
				},
				getRowData : function(rowid) {
					//alert("jqGrid's getRowData")
					//alert(this[0].p.getRowData(rowid));
					var res = {}, resall, getall = false, len, j = 0;
					this
							.each(function() {
								var $t = this, nm, ind;
								if (rowid === undefined) {
									getall = true;
									resall = [];
									len = $t.rows.length;
								} else {
									ind = $($t).jqGrid('getGridRowById', rowid);
									if (!ind) {
										return res;
									}
									len = 2;
								}
								while (j < len) {
									if (getall) {
										ind = $t.rows[j];
									}
									if ($(ind).hasClass('jqgrow')) {
										$('td[role="gridcell"]', ind)
												.each(
														function(i) {
															nm = $t.p.colModel[i].name;
															if (nm !== 'cb'
																	&& nm !== 'subgrid'
																	&& nm !== 'rn') {
																if ($t.p.treeGrid === true
																		&& nm === $t.p.ExpandColumn) {
																	res[nm] = $.jgrid
																			.htmlDecode($(
																					"span:first",
																					this)
																					.html());
																} else {
																	try {
																		res[nm] = $.unformat
																				.call(
																						$t,
																						this,
																						{
																							rowId : ind.id,
																							colModel : $t.p.colModel[i]
																						},
																						i);
																	} catch (e) {
																		res[nm] = $.jgrid
																				.htmlDecode($(
																						this)
																						.html());
																	}
																}
															}
														});
										if (getall) {
											resall.push(res);
											res = {};
										}
									}
									j++;
								}
							});
					return resall || res;
				},
				delRowData : function(rowid) {
					var success = false, rowInd, ia, nextRow;
					this
							.each(function() {
								var $t = this;
								rowInd = $($t).jqGrid('getGridRowById', rowid);
								if (!rowInd) {
									return false;
								}
								if ($t.p.subGrid) {
									nextRow = $(rowInd).next();
									if (nextRow.hasClass('ui-subgrid')) {
										nextRow.remove();
									}
								}
								$(rowInd).remove();
								$t.p.records--;
								$t.p.reccount--;
								$t.updatepager(true, false);
								success = true;
								if ($t.p.multiselect) {
									ia = $.inArray(rowid, $t.p.selarrrow);
									if (ia !== -1) {
										$t.p.selarrrow.splice(ia, 1);
									}
								}
								if ($t.p.multiselect
										&& $t.p.selarrrow.length > 0) {
									$t.p.selrow = $t.p.selarrrow[$t.p.selarrrow.length - 1];
								} else {
									$t.p.selrow = null;
								}
								if ($t.p.datatype === 'local') {
									var id = $.jgrid.stripPref($t.p.idPrefix,
											rowid), pos = $t.p._index[id];
									if (pos !== undefined) {
										$t.p.data.splice(pos, 1);
										$t.refreshIndex();
									}
								}
								if ($t.p.altRows === true && success) {
									var cn = $t.p.altclass;
									$($t.rows).each(function(i) {
										if (i % 2 === 1) {
											$(this).addClass(cn);
										} else {
											$(this).removeClass(cn);
										}
									});
								}
							});
					return success;
				},
				setRowData : function(rowid, data, cssp) {
					var nm, success = true, title;
					this
							.each(function() {
								if (!this.grid) {
									return false;
								}
								var t = this, vl, ind, cp = typeof cssp, lcdata = {};
								ind = $(this).jqGrid('getGridRowById', rowid);
								if (!ind) {
									return false;
								}
								if (data) {
									try {
										$(this.p.colModel)
												.each(
														function(i) {
															nm = this.name;
															var dval = $.jgrid
																	.getAccessor(
																			data,
																			nm);
															if (dval !== undefined) {
																lcdata[nm] = this.formatter
																		&& typeof this.formatter === 'string'
																		&& this.formatter === 'date' ? $.unformat.date
																		.call(
																				t,
																				dval,
																				this)
																		: dval;
																vl = t
																		.formatter(
																				rowid,
																				dval,
																				i,
																				data,
																				'edit');
																title = this.title ? {
																	"title" : $.jgrid
																			.stripHtml(vl)
																}
																		: {};
																if (t.p.treeGrid === true
																		&& nm === t.p.ExpandColumn) {
																	$(
																			"td[role='gridcell']:eq("
																					+ i
																					+ ") > span:first",
																			ind)
																			.html(
																					vl)
																			.attr(
																					title);
																} else {
																	$(
																			"td[role='gridcell']:eq("
																					+ i
																					+ ")",
																			ind)
																			.html(
																					vl)
																			.attr(
																					title);
																}
															}
														});
										if (t.p.datatype === 'local') {
											var id = $.jgrid.stripPref(
													t.p.idPrefix, rowid), pos = t.p._index[id], key;
											if (t.p.treeGrid) {
												for (key in t.p.treeReader) {
													if (t.p.treeReader
															.hasOwnProperty(key)) {
														delete lcdata[t.p.treeReader[key]];
													}
												}
											}
											if (pos !== undefined) {
												t.p.data[pos] = $.extend(true,
														t.p.data[pos], lcdata);
											}
											lcdata = null;
										}
									} catch (e) {
										success = false;
									}
								}
								if (success) {
									if (cp === 'string') {
										$(ind).addClass(cssp);
									} else if (cssp !== null && cp === 'object') {
										$(ind).css(cssp);
									}
									$(t).triggerHandler(
											"jqGridAfterGridComplete");
								}
							});
					return success;
				},
				addRowData : function(rowid, rdata, pos, src) {
					if (!pos) {
						pos = "last";
					}
					var success = false, nm, row, gi, si, ni, sind, i, v, prp = "", aradd, cnm, cn, data, cm, id;
					if (rdata) {
						if ($.isArray(rdata)) {
							aradd = true;
							pos = "last";
							cnm = rowid;
						} else {
							rdata = [ rdata ];
							aradd = false;
						}
						this
								.each(function() {
									var t = this, datalen = rdata.length;
									ni = t.p.rownumbers === true ? 1 : 0;
									gi = t.p.multiselect === true ? 1 : 0;
									si = t.p.subGrid === true ? 1 : 0;
									if (!aradd) {
										if (rowid !== undefined) {
											rowid = String(rowid);
										} else {
											rowid = $.jgrid.randId();
											if (t.p.keyName !== false) {
												cnm = t.p.keyName;
												if (rdata[0][cnm] !== undefined) {
													rowid = rdata[0][cnm];
												}
											}
										}
									}
									cn = t.p.altclass;
									var k = 0, cna = "", lcdata = {}, air = $
											.isFunction(t.p.afterInsertRow) ? true
											: false;
									while (k < datalen) {
										data = rdata[k];
										row = [];
										if (aradd) {
											try {
												rowid = data[cnm];
												if (rowid === undefined) {
													rowid = $.jgrid.randId();
												}
											} catch (e) {
												rowid = $.jgrid.randId();
											}
											cna = t.p.altRows === true ? (t.rows.length - 1) % 2 === 0 ? cn
													: ""
													: "";
										}
										id = rowid;
										rowid = t.p.idPrefix + rowid;
										if (ni) {
											prp = t.formatCol(0, 1, '', null,
													rowid, true);
											row[row.length] = "<td role=\"gridcell\" class=\"ui-state-default jqGrid-rownum\" "
													+ prp + ">0</td>";
										}
										if (gi) {
											v = "<input role=\"checkbox\" type=\"checkbox\""
													+ " id=\"jqg_"
													+ t.p.id
													+ "_"
													+ rowid
													+ "\" class=\"cbox\"/>";
											prp = t.formatCol(ni, 1, '', null,
													rowid, true);
											row[row.length] = "<td role=\"gridcell\" "
													+ prp + ">" + v + "</td>";
										}
										if (si) {
											row[row.length] = $(t).jqGrid(
													"addSubGridCell", gi + ni,
													1);
										}
										for (i = gi + si + ni; i < t.p.colModel.length; i++) {
											cm = t.p.colModel[i];
											nm = cm.name;
											lcdata[nm] = data[nm];
											v = t.formatter(rowid, $.jgrid
													.getAccessor(data, nm), i,
													data);
											prp = t.formatCol(i, 1, v, data,
													rowid, lcdata);
											row[row.length] = "<td role=\"gridcell\" "
													+ prp + ">" + v + "</td>";
										}
										row.unshift(t.constructTr(rowid, false,
												cna, lcdata, data, false));
										row[row.length] = "</tr>";
										if (t.rows.length === 0) {
											$("table:first", t.grid.bDiv)
													.append(row.join(''));
										} else {
											switch (pos) {
											case 'last':
												$(t.rows[t.rows.length - 1])
														.after(row.join(''));
												sind = t.rows.length - 1;
												break;
											case 'first':
												$(t.rows[0])
														.after(row.join(''));
												sind = 1;
												break;
											case 'after':
												sind = $(t).jqGrid(
														'getGridRowById', src);
												if (sind) {
													if ($(
															t.rows[sind.rowIndex + 1])
															.hasClass(
																	"ui-subgrid")) {
														$(
																t.rows[sind.rowIndex + 1])
																.after(row);
													} else {
														$(sind).after(
																row.join(''));
													}
													sind = sind.rowIndex + 1;
												}
												break;
											case 'before':
												sind = $(t).jqGrid(
														'getGridRowById', src);
												if (sind) {
													$(sind)
															.before(
																	row
																			.join(''));
													sind = sind.rowIndex - 1;
												}
												break;
											}
										}
										if (t.p.subGrid === true) {
											$(t).jqGrid("addSubGrid", gi + ni,
													sind);
										}
										t.p.records++;
										t.p.reccount++;
										$(t).triggerHandler(
												"jqGridAfterInsertRow",
												[ rowid, data, data ]);
										if (air) {
											t.p.afterInsertRow.call(t, rowid,
													data, data);
										}
										k++;
										if (t.p.datatype === 'local') {
											lcdata[t.p.localReader.id] = id;
											t.p._index[id] = t.p.data.length;
											t.p.data.push(lcdata);
											lcdata = {};
										}
									}
									if (t.p.altRows === true && !aradd) {
										if (pos === "last") {
											if ((t.rows.length - 1) % 2 === 1) {
												$(t.rows[t.rows.length - 1])
														.addClass(cn);
											}
										} else {
											$(t.rows).each(function(i) {
												if (i % 2 === 1) {
													$(this).addClass(cn);
												} else {
													$(this).removeClass(cn);
												}
											});
										}
									}
									t.updatepager(true, true);
									success = true;
								});
					}
					return success;
				},
				footerData : function(action, data, format) {
					var nm, success = false, res = {}, title;
					function isEmpty(obj) {
						var i;
						for (i in obj) {
							if (obj.hasOwnProperty(i)) {
								return false;
							}
						}
						return true;
					}
					if (action == undefined) {
						action = "get";
					}
					if (typeof format !== "boolean") {
						format = true;
					}
					action = action.toLowerCase();
					this.each(function() {
						var t = this, vl;
						if (!t.grid || !t.p.footerrow) {
							return false;
						}
						if (action === "set") {
							if (isEmpty(data)) {
								return false;
							}
						}
						success = true;
						$(this.p.colModel).each(
								function(i) {
									nm = this.name;
									if (action === "set") {
										if (data[nm] !== undefined) {
											vl = format ? t.formatter("",
													data[nm], i, data, 'edit')
													: data[nm];
											title = this.title ? {
												"title" : $.jgrid.stripHtml(vl)
											} : {};
											$("tr.footrow td:eq(" + i + ")",
													t.grid.sDiv).html(vl).attr(
													title);
											success = true;
										}
									} else if (action === "get") {
										res[nm] = $(
												"tr.footrow td:eq(" + i + ")",
												t.grid.sDiv).html();
									}
								});
					});
					return action === "get" ? res : success;
				},
				showHideCol : function(colname, show) {
					return this
							.each(function() {
								var $t = this, fndh = false, brd = $.jgrid.cell_width ? 0
										: $t.p.cellLayout, cw;
								if (!$t.grid) {
									return;
								}
								if (typeof colname === 'string') {
									colname = [ colname ];
								}
								show = show !== "none" ? "" : "none";
								var sw = show === "" ? true : false, gh = $t.p.groupHeader
										&& (typeof $t.p.groupHeader === 'object' || $
												.isFunction($t.p.groupHeader));
								if (gh) {
									$($t).jqGrid('destroyGroupHeader', false);
								}
								$(this.p.colModel)
										.each(
												function(i) {
													if ($.inArray(this.name,
															colname) !== -1
															&& this.hidden === sw) {
														if ($t.p.frozenColumns === true
																&& this.frozen === true) {
															return true;
														}
														$("tr[role=rowheader]",
																$t.grid.hDiv)
																.each(
																		function() {
																			$(
																					this.cells[i])
																					.css(
																							"display",
																							show);
																		});
														$($t.rows)
																.each(
																		function() {
																			if (!$(
																					this)
																					.hasClass(
																							"jqgroup")) {
																				$(
																						this.cells[i])
																						.css(
																								"display",
																								show);
																			}
																		});
														if ($t.p.footerrow) {
															$(
																	"tr.footrow td:eq("
																			+ i
																			+ ")",
																	$t.grid.sDiv)
																	.css(
																			"display",
																			show);
														}
														cw = parseInt(
																this.width, 10);
														if (show === "none") {
															$t.p.tblwidth -= cw
																	+ brd;
														} else {
															$t.p.tblwidth += cw
																	+ brd;
														}
														this.hidden = !sw;
														fndh = true;
														$($t)
																.triggerHandler(
																		"jqGridShowHideCol",
																		[
																				sw,
																				this.name,
																				i ]);
													}
												});
								if (fndh === true) {
									if ($t.p.shrinkToFit === true
											&& !isNaN($t.p.height)) {
										$t.p.tblwidth += parseInt(
												$t.p.scrollOffset, 10);
									}
									$($t)
											.jqGrid(
													"setGridWidth",
													$t.p.shrinkToFit === true ? $t.p.tblwidth
															: $t.p.width);
								}
								if (gh) {
									$($t).jqGrid('setGroupHeaders',
											$t.p.groupHeader);
								}
							});
				},
				hideCol : function(colname) {
					return this.each(function() {
						$(this).jqGrid("showHideCol", colname, "none");
					});
				},
				showCol : function(colname) {
					return this.each(function() {
						$(this).jqGrid("showHideCol", colname, "");
					});
				},
				remapColumns : function(permutation, updateCells, keepHeader) {
					function resortArray(a) {
						var ac;
						if (a.length) {
							ac = $.makeArray(a);
						} else {
							ac = $.extend({}, a);
						}
						$.each(permutation, function(i) {
							a[i] = ac[this];
						});
					}
					var ts = this.get(0);
					function resortRows(parent, clobj) {
						$(">tr" + (clobj || ""), parent).each(function() {
							var row = this;
							var elems = $.makeArray(row.cells);
							$.each(permutation, function() {
								var e = elems[this];
								if (e) {
									row.appendChild(e);
								}
							});
						});
					}
					resortArray(ts.p.colModel);
					resortArray(ts.p.colNames);
					resortArray(ts.grid.headers);
					resortRows($("thead:first", ts.grid.hDiv), keepHeader
							&& ":not(.ui-jqGrid-labels)");
					if (updateCells) {
						resortRows($("#" + $.jgrid.jqID(ts.p.id)
								+ " tbody:first"),
								".jqgfirstrow, tr.jqgrow, tr.jqfoot");
					}
					if (ts.p.footerrow) {
						resortRows($("tbody:first", ts.grid.sDiv));
					}
					if (ts.p.remapColumns) {
						if (!ts.p.remapColumns.length) {
							ts.p.remapColumns = $.makeArray(permutation);
						} else {
							resortArray(ts.p.remapColumns);
						}
					}
					ts.p.lastsort = $.inArray(ts.p.lastsort, permutation);
					if (ts.p.treeGrid) {
						ts.p.expColInd = $.inArray(ts.p.expColInd, permutation);
					}
					$(ts).triggerHandler("jqGridRemapColumns",
							[ permutation, updateCells, keepHeader ]);
				},
				setGridWidth : function(nwidth, shrink) {
					return this
							.each(function() {
								if (!this.grid) {
									return;
								}
								var $t = this, cw, initwidth = 0, brd = $.jgrid.cell_width ? 0
										: $t.p.cellLayout, lvc, vc = 0, hs = false, scw = $t.p.scrollOffset, aw, gw = 0, cr;
								if (typeof shrink !== 'boolean') {
									shrink = $t.p.shrinkToFit;
								}
								if (isNaN(nwidth)) {
									return;
								}
								nwidth = parseInt(nwidth, 10);
								$t.grid.width = $t.p.width = nwidth;
								$("#gbox_" + $.jgrid.jqID($t.p.id)).css(
										"width", nwidth + "px");
								$("#gview_" + $.jgrid.jqID($t.p.id)).css(
										"width", nwidth + "px");
								$($t.grid.bDiv).css("width", nwidth + "px");
								$($t.grid.hDiv).css("width", nwidth + "px");
								if ($t.p.pager) {
									$($t.p.pager).css("width", nwidth + "px");
								}
								if ($t.p.toppager) {
									$($t.p.toppager)
											.css("width", nwidth + "px");
								}
								if ($t.p.toolbar[0] === true) {
									$($t.grid.uDiv).css("width", nwidth + "px");
									if ($t.p.toolbar[1] === "both") {
										$($t.grid.ubDiv).css("width",
												nwidth + "px");
									}
								}
								if ($t.p.footerrow) {
									$($t.grid.sDiv).css("width", nwidth + "px");
								}
								if (shrink === false && $t.p.forceFit === true) {
									$t.p.forceFit = false;
								}
								if (shrink === true) {
									$.each($t.p.colModel, function() {
										if (this.hidden === false) {
											cw = this.widthOrg;
											initwidth += cw + brd;
											if (this.fixed) {
												gw += cw + brd;
											} else {
												vc++;
											}
										}
									});
									if (vc === 0) {
										return;
									}
									$t.p.tblwidth = initwidth;
									aw = nwidth - brd * vc - gw;
									if (!isNaN($t.p.height)) {
										if ($($t.grid.bDiv)[0].clientHeight < $($t.grid.bDiv)[0].scrollHeight
												|| $t.rows.length === 1) {
											hs = true;
											aw -= scw;
										}
									}
									initwidth = 0;
									var cle = $t.grid.cols.length > 0;
									$
											.each(
													$t.p.colModel,
													function(i) {
														if (this.hidden === false
																&& !this.fixed) {
															cw = this.widthOrg;
															cw = Math
																	.round(aw
																			* cw
																			/ ($t.p.tblwidth
																					- brd
																					* vc - gw));
															if (cw < 0) {
																return;
															}
															this.width = cw;
															initwidth += cw;
															$t.grid.headers[i].width = cw;
															$t.grid.headers[i].el.style.width = cw
																	+ "px";
															if ($t.p.footerrow) {
																$t.grid.footers[i].style.width = cw
																		+ "px";
															}
															if (cle) {
																$t.grid.cols[i].style.width = cw
																		+ "px";
															}
															lvc = i;
														}
													});

									if (!lvc) {
										return;
									}

									cr = 0;
									if (hs) {
										if (nwidth - gw
												- (initwidth + brd * vc) !== scw) {
											cr = nwidth - gw
													- (initwidth + brd * vc)
													- scw;
										}
									} else if (Math.abs(nwidth - gw
											- (initwidth + brd * vc)) !== 1) {
										cr = nwidth - gw
												- (initwidth + brd * vc);
									}
									$t.p.colModel[lvc].width += cr;
									$t.p.tblwidth = initwidth + cr + brd * vc
											+ gw;
									if ($t.p.tblwidth > nwidth) {
										var delta = $t.p.tblwidth
												- parseInt(nwidth, 10);
										$t.p.tblwidth = nwidth;
										cw = $t.p.colModel[lvc].width = $t.p.colModel[lvc].width
												- delta;
									} else {
										cw = $t.p.colModel[lvc].width;
									}
									$t.grid.headers[lvc].width = cw;
									$t.grid.headers[lvc].el.style.width = cw
											+ "px";
									if (cle) {
										$t.grid.cols[lvc].style.width = cw
												+ "px";
									}
									if ($t.p.footerrow) {
										$t.grid.footers[lvc].style.width = cw
												+ "px";
									}
								}
								if ($t.p.tblwidth) {
									$('table:first', $t.grid.bDiv).css("width",
											$t.p.tblwidth + "px");
									$('table:first', $t.grid.hDiv).css("width",
											$t.p.tblwidth + "px");
									$t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
									if ($t.p.footerrow) {
										$('table:first', $t.grid.sDiv).css(
												"width", $t.p.tblwidth + "px");
									}
								}
							});
				},
				setGridHeight : function(nh) {
					return this.each(function() {
						var $t = this;
						if (!$t.grid) {
							return;
						}
						var bDiv = $($t.grid.bDiv);
						bDiv.css({
							height : nh + (isNaN(nh) ? "" : "px")
						});
						if ($t.p.frozenColumns === true) {
							//follow the original set height to use 16, better scrollbar width detection
							$('#' + $.jgrid.jqID($t.p.id) + "_frozen").parent()
									.height(bDiv.height() - 16);
						}
						$t.p.height = nh;
						if ($t.p.scroll) {
							$t.grid.populateVisible();
						}
					});
				},
				setCaption : function(newcap) {
					return this.each(function() {
						this.p.caption = newcap;
						$("span.ui-jqGrid-title, span.ui-jqGrid-title-rtl",
								this.grid.cDiv).html(newcap);
						$(this.grid.cDiv).show();
					});
				},
				setLabel : function(colname, nData, prop, attrp) {
					return this.each(function() {
						var $t = this, pos = -1;
						if (!$t.grid) {
							return;
						}
						if (colname !== undefined) {
							$($t.p.colModel).each(function(i) {
								if (this.name === colname) {
									pos = i;
									return false;
								}
							});
						} else {
							return;
						}
						if (pos >= 0) {
							var thecol = $("tr.ui-jqGrid-labels th:eq(" + pos
									+ ")", $t.grid.hDiv);
							if (nData) {
								var ico = $(".s-ico", thecol);
								$("[id^=jqgh_]", thecol).empty().html(nData)
										.append(ico);
								$t.p.colNames[pos] = nData;
							}
							if (prop) {
								if (typeof prop === 'string') {
									$(thecol).addClass(prop);
								} else {
									$(thecol).css(prop);
								}
							}
							if (typeof attrp === 'object') {
								$(thecol).attr(attrp);
							}
						}
					});
				},
				setCell : function(rowid, colname, nData, cssp, attrp, forceupd) {
					return this
							.each(function() {
								var $t = this, pos = -1, v, title;
								if (!$t.grid) {
									return;
								}
								if (isNaN(colname)) {
									$($t.p.colModel).each(function(i) {
										if (this.name === colname) {
											pos = i;
											return false;
										}
									});
								} else {
									pos = parseInt(colname, 10);
								}
								if (pos >= 0) {
									var ind = $($t).jqGrid('getGridRowById',
											rowid);
									if (ind) {
										var tcell = $("td:eq(" + pos + ")", ind);
										if (nData !== "" || forceupd === true) {
											v = $t.formatter(rowid, nData, pos,
													ind, 'edit');
											title = $t.p.colModel[pos].title ? {
												"title" : $.jgrid.stripHtml(v)
											}
													: {};
											if ($t.p.treeGrid
													&& $(".tree-wrap", $(tcell)).length > 0) {
												$("span", $(tcell)).html(v)
														.attr(title);
											} else {
												$(tcell).html(v).attr(title);
											}
											if ($t.p.datatype === "local") {
												var cm = $t.p.colModel[pos], index;
												nData = cm.formatter
														&& typeof cm.formatter === 'string'
														&& cm.formatter === 'date' ? $.unformat.date
														.call($t, nData, cm)
														: nData;
												index = $t.p._index[$.jgrid
														.stripPref(
																$t.p.idPrefix,
																rowid)];
												if (index !== undefined) {
													$t.p.data[index][cm.name] = nData;
												}
											}
										}
										if (typeof cssp === 'string') {
											$(tcell).addClass(cssp);
										} else if (cssp) {
											$(tcell).css(cssp);
										}
										if (typeof attrp === 'object') {
											$(tcell).attr(attrp);
										}
									}
								}
							});
				},
				getCell : function(rowid, col) {
					var ret = false;
					this.each(function() {
						var $t = this, pos = -1;
						if (!$t.grid) {
							return;
						}
						if (isNaN(col)) {
							$($t.p.colModel).each(function(i) {
								if (this.name === col) {
									pos = i;
									return false;
								}
							});
						} else {
							pos = parseInt(col, 10);
						}
						if (pos >= 0) {
							var ind = $($t).jqGrid('getGridRowById', rowid);
							if (ind) {
								try {
									ret = $.unformat.call($t, $("td:eq(" + pos
											+ ")", ind), {
										rowId : ind.id,
										colModel : $t.p.colModel[pos]
									}, pos);
								} catch (e) {
									ret = $.jgrid.htmlDecode($(
											"td:eq(" + pos + ")", ind).html());
								}
							}
						}
					});
					return ret;
				},
				getCol : function(col, obj, mathopr) {
					var ret = [], val, sum = 0, min, max, v;
					obj = typeof obj !== 'boolean' ? false : obj;
					if (mathopr === undefined) {
						mathopr = false;
					}
					this
							.each(function() {
								var $t = this, pos = -1;
								if (!$t.grid) {
									return;
								}
								if (isNaN(col)) {
									$($t.p.colModel).each(function(i) {
										if (this.name === col) {
											pos = i;
											return false;
										}
									});
								} else {
									pos = parseInt(col, 10);
								}
								if (pos >= 0) {
									var ln = $t.rows.length, i = 0, dlen = 0;
									if (ln && ln > 0) {
										while (i < ln) {
											if ($($t.rows[i])
													.hasClass('jqgrow')) {
												try {
													val = $.unformat
															.call(
																	$t,
																	$($t.rows[i].cells[pos]),
																	{
																		rowId : $t.rows[i].id,
																		colModel : $t.p.colModel[pos]
																	}, pos);
												} catch (e) {
													val = $.jgrid
															.htmlDecode($t.rows[i].cells[pos].innerHTML);
												}
												if (mathopr) {
													v = parseFloat(val);
													if (!isNaN(v)) {
														sum += v;
														if (max === undefined) {
															max = min = v;
														}
														min = Math.min(min, v);
														max = Math.max(max, v);
														dlen++;
													}
												} else if (obj) {
													ret.push({
														id : $t.rows[i].id,
														value : val
													});
												} else {
													ret.push(val);
												}
											}
											i++;
										}
										if (mathopr) {
											switch (mathopr.toLowerCase()) {
											case 'sum':
												ret = sum;
												break;
											case 'avg':
												ret = sum / dlen;
												break;
											case 'count':
												ret = (ln - 1);
												break;
											case 'min':
												ret = min;
												break;
											case 'max':
												ret = max;
												break;
											}
										}
									}
								}
							});
					return ret;
				},
				clearGridData : function(clearfooter) {
					return this
							.each(function() {
								var $t = this;
								if (!$t.grid) {
									return;
								}
								if (typeof clearfooter !== 'boolean') {
									clearfooter = false;
								}
								if ($t.p.deepempty) {
									$(
											"#" + $.jgrid.jqID($t.p.id)
													+ " tbody:first tr:gt(0)")
											.remove();
								} else {
									var trf = $("#" + $.jgrid.jqID($t.p.id)
											+ " tbody:first tr:first")[0];
									$(
											"#" + $.jgrid.jqID($t.p.id)
													+ " tbody:first").empty()
											.append(trf);
								}
								if ($t.p.footerrow && clearfooter) {
									$(".ui-jqGrid-ftable td", $t.grid.sDiv)
											.html("&#160;");
								}
								$t.p.selrow = null;
								$t.p.selarrrow = [];
								$t.p.savedRow = [];
								$t.p.records = 0;
								$t.p.page = 1;
								$t.p.lastpage = 0;
								$t.p.reccount = 0;
								$t.p.data = [];
								$t.p._index = {};
								$t.updatepager(true, false);
							});
				},
				getInd : function(rowid, rc) {
					var ret = false, rw;
					this.each(function() {
						rw = $(this).jqGrid('getGridRowById', rowid);
						if (rw) {
							ret = rc === true ? rw : rw.rowIndex;
						}
					});
					return ret;
				},
				bindKeys : function(settings) {
					var o = $.extend({
						onEnter : null,
						onSpace : null,
						onLeftKey : null,
						onRightKey : null,
						scrollingRows : true
					}, settings || {});
					return this
							.each(function() {
								var $t = this;
								if (!$('body').is('[role]')) {
									$('body').attr('role', 'application');
								}
								$t.p.scrollrows = o.scrollingRows;
								$($t)
										.keydown(
												function(event) {
													var target = $($t).find(
															'tr[tabindex=0]')[0], id, r, mind, expanded = $t.p.treeReader.expanded_field;
													//check for arrow keys
													if (target) {
														mind = $t.p._index[$.jgrid
																.stripPref(
																		$t.p.idPrefix,
																		target.id)];
														if (event.keyCode === 37
																|| event.keyCode === 38
																|| event.keyCode === 39
																|| event.keyCode === 40) {
															// up key
															if (event.keyCode === 38) {
																r = target.previousSibling;
																id = "";
																if (r) {
																	if ($(r)
																			.is(
																					":hidden")) {
																		while (r) {
																			r = r.previousSibling;
																			if (!$(
																					r)
																					.is(
																							":hidden")
																					&& $(
																							r)
																							.hasClass(
																									'jqgrow')) {
																				id = r.id;
																				break;
																			}
																		}
																	} else {
																		id = r.id;
																	}
																}
																$($t)
																		.jqGrid(
																				'setSelection',
																				id,
																				true,
																				event);
																event
																		.preventDefault();
															}
															//if key is down arrow
															if (event.keyCode === 40) {
																r = target.nextSibling;
																id = "";
																if (r) {
																	if ($(r)
																			.is(
																					":hidden")) {
																		while (r) {
																			r = r.nextSibling;
																			if (!$(
																					r)
																					.is(
																							":hidden")
																					&& $(
																							r)
																							.hasClass(
																									'jqgrow')) {
																				id = r.id;
																				break;
																			}
																		}
																	} else {
																		id = r.id;
																	}
																}
																$($t)
																		.jqGrid(
																				'setSelection',
																				id,
																				true,
																				event);
																event
																		.preventDefault();
															}
															// left
															if (event.keyCode === 37) {
																if ($t.p.treeGrid
																		&& $t.p.data[mind][expanded]) {
																	$(target)
																			.find(
																					"div.treeclick")
																			.trigger(
																					'click');
																}
																$($t)
																		.triggerHandler(
																				"jqGridKeyLeft",
																				[ $t.p.selrow ]);
																if ($
																		.isFunction(o.onLeftKey)) {
																	o.onLeftKey
																			.call(
																					$t,
																					$t.p.selrow);
																}
															}
															// right
															if (event.keyCode === 39) {
																if ($t.p.treeGrid
																		&& !$t.p.data[mind][expanded]) {
																	$(target)
																			.find(
																					"div.treeclick")
																			.trigger(
																					'click');
																}
																$($t)
																		.triggerHandler(
																				"jqGridKeyRight",
																				[ $t.p.selrow ]);
																if ($
																		.isFunction(o.onRightKey)) {
																	o.onRightKey
																			.call(
																					$t,
																					$t.p.selrow);
																}
															}
														}
														//check if enter was pressed on a grid or treegrid node
														else if (event.keyCode === 13) {
															$($t)
																	.triggerHandler(
																			"jqGridKeyEnter",
																			[ $t.p.selrow ]);
															if ($
																	.isFunction(o.onEnter)) {
																o.onEnter
																		.call(
																				$t,
																				$t.p.selrow);
															}
														} else if (event.keyCode === 32) {
															$($t)
																	.triggerHandler(
																			"jqGridKeySpace",
																			[ $t.p.selrow ]);
															if ($
																	.isFunction(o.onSpace)) {
																o.onSpace
																		.call(
																				$t,
																				$t.p.selrow);
															}
														}
													}
												});
							});
				},
				unbindKeys : function() {
					return this.each(function() {
						$(this).unbind('keydown');
					});
				},
				getLocalRow : function(rowid) {
					var ret = false, ind;
					this.each(function() {
						if (rowid !== undefined) {
							ind = this.p._index[$.jgrid.stripPref(
									this.p.idPrefix, rowid)];
							if (ind >= 0) {
								ret = this.p.data[ind];
							}
						}
					});
					return ret;
				},

				initGrid : function() {
					this
							.each(function() {
								var $t = this;
								//显示
								//要排除不存在的参数
								/*	for(i in gridParam){
										if(typeof (this.p[i])!= 'undefined')
											this.p[i]=gridParam[i];
									}
								 */
								this.p.width_sum = 0;
								for (var i = 0; i < this.p.colModel.length; i++) {
									this.p.width_sum += this.p.colModel[i].width;

								}
								var gridHeadhtml = "<div><table class='table'> <thead> <tr>  ";
								if(this.p.multiselect){
									gridHeadhtml += "<th style=\"width:5%;\" ><input type='checkbox' class='cbox' id='cb_"+this.id+"' name='cb_"+this.id+"' > </th>";
								}
								for (var i = 0; i < this.p.colNames.length; i++) {

									if (this.p.colModel[i].width == 0) {
										gridHeadhtml += "<th style='display:none' width=\""
												+ this.p.colModel[i].width
												/ this.p.width_sum
												* 100
												+ "%\">"
												+ this.p.colNames[i]
												+ "</th>";
									} else

										gridHeadhtml += "<th width=\""
												+ this.p.colModel[i].width
												/ this.p.width_sum * 100
												+ "%\">" + this.p.colNames[i]
												+ "</th>";
								}
								gridHeadhtml += "</tr></thead></table></div>";
								//		grid=gridParam;
								var gridTableHtml = "<div class=\"grid-content\"></div>";
								var pageHtml="";
								//var pageHtml = "<nav class=\"nav\">        <ul class=\"pagination pagination-lg\">         <li><a href=\"#\" aria-label=\"Previous\"><span aria-hidden=\"true\">上一页</span></a>         </li><li class=\"active\"><a href=\"#\">1</a>         </li><li><a href=\"#\">2</a>         </li><li><a href=\"#\">3</a>         </li><li><a href=\"#\">4</a>         </li><li><a href=\"#\">5</a>         </li><li><a href=\"#\" aria-label=\"Next\"><span aria-hidden=\"true\">下一页</span></a>        </li></ul>       </nav>";

								if(this.p.gridHead!=null){
									$(this.p.grid_selector).html(
											"<div>" + $(this.p.gridHead).html() + gridTableHtml
													+ "</div>");
								}else
								$(this.p.grid_selector).html(
										"<div>" + gridHeadhtml + gridTableHtml
												+ "</div>");
								//		this.p.width_sum=width_sum;
								$(this.p.pager_selector).html(pageHtml);

								if(this.p.multiselect){
									var id = this.id;
									$(this.p.grid_selector).find(
											"input[name='cb_"+this.id+"']").change({
										id : this.p.grid_selector
									}, function(event) {
										$(event.data.id).jqGrid("selectAll");
									});
								}
								//		$(this.p.gridParam.pager_selector).find("li").first().on("click",this.p.goPre.Apply(this));
								//		$(this.gridParam.pager_selector).find("li").last().on("click",this.goNext.Apply(this));
								$($t).jqGrid("ajaxRequest");

							});
				},
				initParam : function() {
					this.each(function() {

						this.p.searchParams.curPage = this.p.page.curPage;
						this.p.searchParams.pageSize = this.p.page.pageSize;
					});

				},
				getSelRow : function() {
					var ret;
					this.each(function() {
						var $t = this;
						ret = this.p.selrow;
					});
					return ret;
				},
				search : function(json) {

					this.each(function() {
						var $t = this;
						this.p.searchParams = json;
						if(this.p.page){
							this.p.page.curpage = 1;
						}else{
							this.p.page={};
							this.p.page.curpage = 1;
							this.p.page.pagesize = 10;
						}
						$($t).jqGrid("ajaxRequest");
					});

				},
				getRowNum : function() {

					var num;
					this.each(function() {
						var $t = this;

						if (this.p.data == null)
							alert("grid has no data");
						num = this.p.data.length;

					});
					return num;
					//	return (this.p.data || []).length;
				},
				goPre : function(event) {
					var $t = event.data.t;

					if ($t.p.page.curPage == 1)
						return;
					else
						$t.p.page.curPage--;

					$($t).jqGrid("ajaxRequest");
				},
				selectAll : function() {//alert($(this).jqGrid("getGridParam","selarrrow"));
					this.each(function() {
						var $t = this;

						var status = $(this).find("input[name='cb_"+this.id+"']").is(":checked");

						this.p.selarrrow=[];
							$(this).find("input[type='checkbox']").each(function(index,element){
								$(this).prop("checked",status);
								if(status){
									$t.p.selarrrow.push(index);
								}
							});

					});
				},

				goNext : function(event) {
					var $t = event.data.t;
					//console.log("curPage:"+$t.p.page.curPage+"totalPage")
					if ($t.p.page.curPage >= $t.p.page.totalPage)
						return;
					$t.p.page.curPage++;
					$($t).jqGrid("ajaxRequest");

				},
				goPage : function(event) {
					var $t = event.data.t;
					$t.p.page.curPage = event.data.curPage;
					$($t).jqGrid("ajaxRequest");
				},
				getCell : function(rownum, colnum) {
					var ret;
					this.each(function() {
						var $t = this;
						/*	var colnum=0;
							for(var i=0;i<colNames.length;i++){
								if(colname== colNames[i]){
									colnum=i;
									break;
								}
							}*/
						ret = $(this).find("tr").eq(rownum + 1).find("td").eq(
								colnum);
					});
					return ret;
				},
				getRowData : function(rowId) {
					var data;
					this.each(function() {
						var $t = this;
						if (this.p.data != null)
							data= this.p.data[rowId];
						else
							data= null;
					});
					return data;
				},
				reloadGrid : function() {
					this.each(function() {
						this.p.page.curPage=1;
						var $t = this;
						$(this).jqGrid("ajaxRequest");
					});
					//		$.post(this.p.url,this.p.searchParams,this.p.ajaxCallBack);
				},
				ajaxRequest : function() {
					showWait();
					this.each(function() {
						var $t = this;
						//合并参数
						$(this).jqGrid("initParam");
						//		this.p.searchParams.curPage= this.p.page.curPage;
						//		this.p.searchParams.pageSize= this.p.page.pageSize;

						/* Protocol.request(this.p.url,{parameter:this.p.searchParams,
				                success:$.fn.jqGrid.ajaxCallBack.Apply($(this)),scope:$t});  */
						/*$.post(this.p.url, this.p.searchParams,function(data){
							ajaxResultHandler(data);
							$($t).jqGrid("ajaxCallBack",data);
						});*/
						if(this.p.searchParams.curPage==null){
							this.p.searchParams.curPage=1;
							this.p.searchParams.pageSize=10;
						}
						 $.ajax({

				             type: "POST",

				             url: this.p.url,

				             data:this.p.searchParams,

				             dataType: "json",
				             error:function(XMLHttpRequest, textStatus, errorThrown){
				            	 hideWait();
				            	 if(XMLHttpRequest.status=403){
				            		 try{
				            		 var result =  eval("("+XMLHttpRequest.responseText+")");
				            		if(result){
				            		 if(result.r=505){
				            			 zalert(result.msg);
				            			 return;
				            		 }
				            		}else{
				            			zalert(result);return;
				            		}
				            		 }catch(e){

				            		 }

				            	 }
				            	 zalert("请求异常");
				            	 return;
				                 //通常情况下textStatus和errorThrown只有其中一个包含信息

				                // this;   //调用本次ajax请求时传递的options参数

				              },

				             success: function(data){ hideWait();

			            				$($t).jqGrid("ajaxCallBack",data);

				                       /*  $('#resText').empty();   //清空resText里面的所有内容

				                         var html = '';

				                         $.each(data, function(commentIndex, comment){

				                               html += '<div class="comment"><h6>' + comment['username']

				                                         + ':</h6><p class="para"' + comment['content']

				                                         + '</p></div>';

				                         });

				                         $('#resText').html(html);*/

				                      }

				         });
					});
				},
				getGridParam : function(pName) {
					var result;
					this.each(function(){

						if(pName=="selarrrow"){
							this.p[pName]=[];
							var arr = this.p[pName];

							//alert($(this).find(".table").find("input:checkbox:checked").length)
							$(this).find(".grid-content").find("input:checkbox:checked").each(function(){
							/*	var status = $(this).is(":checked");
								if(status)*/
									arr.push(this.id.split("_")[2]);
							});
						}
						result= this.p[pName] != undefined ? this.p[pName] : null;
					});
					return result;
				},
				getDataValue : function(i, name) {

					return this.p.data[i][name];
				},
				myGrid : function() {

				},
				ajaxCallBack : function(result) {
					this.each(function() {
						var $t = this;
						if (typeof (result) == 'string')
							result = eval("(" + result + ")");
						if (!ajaxResultHandler(result))
							return;
						this.p.data = result.data;
						this.p.page = result.page;
						if(result.r!=AJAX_SUCC){
							alert(result.msg);
							return;
						}
						this.p.rowNum = result.data.length;

						/*
						var html="<div><table class='table'><tbody>";

						for(var i=0;i<result.data.length;i++){
							html+="<tr>";
							for(var j=0;j<this.gridParam.colModel.length;j++){
								var colName=this.gridParam.colModel[j].name;
								var value=result.data[i][colName];
								html+="<td width=\""+this.gridParam.colModel[j].width/this.width_sum*100+"%\">"+value+"</td>";
							}
							html+="</tr>";
						}
						html+="</tbody></table></div>";
						$(this.gridParam.grid_selector).find(".grid-content").html(html);
						freshPageNation();*/

						$(this).jqGrid("render");
						this.p.selrow = null;
						if (this.p.loadComplete != null)
							this.p.loadComplete.call(this);
						if(this.p.multiselect){
							var id =this.id;
							$(this.p.grid_selector).find(".grid-content").find(
							"input[type='checkbox']").change( {
								id :id
							}, function(event) {
								$("#"+event.data.id).find(
										"#cb_"+event.data.id).attr("checked",false);
							});
						}
					});
				},
				render : function() {
					this.each(function() {
						var $t = this;
						$(this).jqGrid("renderGrid");
						$(this).jqGrid("renderPage");
					});
				},
				renderGrid : function() {
					this
							.each(function() {
								var $t = this;
								var html = "<div><table class='table'>";
								if(this.p.data.length==0){
								html += "<thead><div style='text-align:center;'>暂无数据</div></thead>";
								}

								html += "<tbody>";
								for (var i = 0; i < this.p.data.length; i++) {
									html += "<tr >";
									if(this.p.multiselect){
										html += "<td  style=\"width:5%;text-align:center;padding:0\" ><input type='checkbox' class='cbox' id='jqg_"+this.id+"_"+i+"' name='jqg_"+this.id+"_"+i+"' > </td>";
									}
									for (var j = 0; j < this.p.colModel.length; j++) {
										var colName = this.p.colModel[j].name;
										var value = this.p.data[i][colName];
										if (typeof (value) == 'undefined') {
											value = '';
										}

										if (this.p.colModel[j].width == 0) {
											html += "<td style='display:none' width=\""
													+ this.p.colModel[j].width
													/ this.p.width_sum
													* 100
													+ "%\">"
													+ this.p.colNames[j]
													+ "</td>";
										} else if (typeof (this.p.colModel[j].formatter) != 'undefined') {
											html += "<td width=\""
													+ this.p.colModel[j].width
													/ this.p.width_sum
													* 100
													+ "%\">"
													+ this.p.colModel[j].formatter.call(this,value,this.p,this.p.data[i]);
											+"</td>";
										} else
											html += "<td width=\""
													+ this.p.colModel[j].width
													/ this.p.width_sum * 100
													+ "%\">" + value + "</td>";
									}
									html += "</tr>";

								}
								html += "</tbody></table></div>";

								//tr 添加事件 如果选中就添加样式
								/*tr.click(function(){
									$(".grid-content").find(".selected").removeClass("selected");
									$(this).addClass("selected");
								});*/

								var end_html = $(html);

								$(this).find(".grid-content")
										.html(end_html);
								var that = this.p;
								$(this).find(".grid-content")
										.find("tr")
										.each(
												function(index, element) {
													$(this).click({id : index,grid : that},
																	function(	event) {
																		$(".grid-content").find(".selected").removeClass("selected");
																		$(this).addClass("selected");
																		//				alert(event.data.id);
																		//				alert(event.data.grid);
																		//				alert(event.data.grid.selrow);
																		event.data.grid.selrow = event.data.id;

																		if(that.onSelectRow){
																			that.onSelectRow.call($t,index);
																		}


					                                                    if(that.ondblClickRow){
					                                                        $(this).dblclick({id : index,grid : that},
					                                                                function(    event) {
					                                                                    //$(".grid-content").find(".selected").removeClass("selected");
					                                                                    //$(this).addClass("selected");
					                                                                    event.data.grid.selrow = event.data.id;
					                                                                    if(typeof that.ondblClickRow!= 'undefined'){
					                                                                        that.ondblClickRow.call(this,index);
					                                                                    }

					                                                                });
					                                                        }


																	});
												});
							});
				},
				renderPage : function() {
					//首先判断是否时候有加载数据了
					this
							.each(function() {
								var $t = this;

								var page = this.p.page;
								if (page == null)
									return;
								//一般是显示5个页数
								//从 curPage -2 开始 到 curPage+2
								var total = 1;
								var pageHtml = "<nav class=\"nav\"> <ul class=\"pagination pagination-sm\"><li><a href=\"javascript:void(0)\" class=\"page_bg pre \" aria-label=\"Previous\">上一页</a>";

								var min = 0, max = page.totalPage;
								var middel = page.curPage;
								var _min = 0, _max = 0;
								_min = (middel - 2) < 1 ? 1 : (middel - 2);
								_max = (middel + 2) > max ? max : (middel + 2);

								if (_min == 1) {
									_max = middel + (5 - middel - _min + 1);
									if (_max >= max)
										_max = max;
								}
								if (_max == max) {
									_min = middel - 5 + (max - middel) + 1;
									if (_min <= 1) {
										_min = 1;
									}
								}
								if(_min>1){
									pageHtml += "<li class=\"  num\"><a href=\"javascript:void(0)\" >"+1+"</a></li><li >...</li>";
								}
								for (var i = _min; i <= _max; i++) {
									if (i == page.curPage)
										pageHtml += "<li class=\"  num\" ><span class='curr page_bg'>"
												+ i + "</span></li>";
									else
										pageHtml += "<li class=\"num\" ><a href=\"javascript:void(0)\" >" + i
												+ "</a></li>";
								}


								if(_max<page.totalPage){
									pageHtml += "<li >...</li><li class=\"  num\" ><a href=\"javascript:void(0)\" >"+page.totalPage+"</a></li>";
								}
								//加上最后一页 和...号
								/*
								for(var i=page.curPage-4;i<page.curPage+4;i++){
									if(i<1){
										continue;
									}
									total++;
									if(i==page.curPage)
									pageHtml+="<li class=\"active\" ><a href=\"#\">"+i+"</a></li>";
									else
										pageHtml+="<li  ><a href=\"#\" >"+i+"</a></li>";
									if(total>10)
										break;
									if(i>=page.totalPage)
										break;
								}*/
								//console.log(page);
								pageHtml += "<li><a href=\"javascript:void(0)\" class=\"page_bg next\" aria-label=\"Next\">下一页</a></li><span>共"+page.totalPage+"页，"+page.totalCount+"条信息</span></ul></nav>";

								$(this.p.pager_selector).html(pageHtml);
								$(this.p.pager_selector).find(".pre")
										.click({
											t : this
										}, $.fn.jqGrid.goPre);
								$(this.p.pager_selector).find(".next")
										.click({
											t : this
										}, $.fn.jqGrid.goNext);
								var eles = $(this.p.pager_selector).find(".num");
								for (var i = 0; i < eles.length ; i++) {

									$(eles[i]).click({
										curPage : $(eles[i]).text(),
										t : this
									}, $.fn.jqGrid.goPage);
								}
							});
				},

				afterRender : function() {

				},
				loadData : function(result) {

				},
				getCheckedCellData:function(rowName){
					var ary=new Array();
					this
					.each(function() {
						var ids=$(this).jqGrid("getGridParam","selarrrow");
						var data = this.rows
						for(var i=0,lenght=ids.length;i<length;i++){
							ary.push(data[rowName]);
						}
						return ary;
					});
				},
			});
	//闭包结束
})(jQuery);
