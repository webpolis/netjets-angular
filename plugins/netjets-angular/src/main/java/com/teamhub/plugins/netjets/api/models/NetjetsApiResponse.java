package com.teamhub.plugins.netjets.api.models;

import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamhub.infrastructure.hibernate.SessionFactoryWrapper;
import com.teamhub.models.site.Container;
import com.teamhub.models.site.Network;
import com.teamhub.plugins.netjets.api.utils.NetjetsApiJsonUtils;
import com.teamhub.plugins.netjets.managers.NetjetsApiManager;

@Service
public class NetjetsApiResponse {
	private JSONObject opts;
	private Container container;

	@Autowired
	static NetjetsApiManager apiManager;

	@Autowired
	NetjetsApiJsonUtils jsonUtils;

	@Autowired
	SessionFactoryWrapper sessionFactoryWrapper;

	public String process(final JSONObject opts, final Container container) {
		this.opts = opts;
		this.container = container;

		if ((this.opts == null) || (this.opts.length() == 0)
				|| (this.container == null)) {
			return null;
		}

		String ret = null;

		try {
			if (this.opts.get("action").equals("listBySpace")) {
				final String space = this.opts.getString("space");
				final String sort = this.opts.getString("sort");
				final int page = this.opts.getInt("page");
				final int pageSize = this.opts.getInt("pageSize");

				ret = (String) this.sessionFactoryWrapper.runOnNetwork(
						new Callable() {
							@Override
							public Object call() throws Exception {
								return NetjetsApiResponse.this.jsonUtils
										.encodeQuestionsList(NetjetsApiResponse.apiManager
												.getQuestionsBySpace(container,
														space, sort, page,
														pageSize));
							}
						}, (Network) container.getParent());
			}
		} catch (final JSONException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
