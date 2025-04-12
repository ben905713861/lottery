function request(method, url, data) {
  method = method.toUpperCase();
  let fetchRes;
  if (method == 'GET' || method == 'DELETE') {
    let fullUrl = url;
    if (data != null) {
      const queryString = Object.keys(data)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(data[key])}`)
        .join('&');
        fullUrl += '?' + queryString;
    }
    fetchRes = fetch(fullUrl, {
      method,
      headers: {
        uid: localStorage.getItem('uid'),
        registerTime: localStorage.getItem('registerTime'),
      },
    });
  } else {
    let body;
    if (data == null) {
      body = null;
    } else if (typeof data == 'string') {
      body = data;
    } else {
      body = JSON.stringify(data);
    }
    fetchRes = fetch(url, {
      method,
      body,
      headers: {
        'Content-Type': 'application/json',
        uid: localStorage.getItem('uid'),
        registerTime: localStorage.getItem('registerTime'),
      },
    });
  }
  return fetchRes.then(resp => resp.json()).then(respBody => {
    if (respBody.status != 0) {
      throw respBody.status + ': ' + respBody.msg;
    }
    return respBody.data;
  }).catch(errorMsg => {
    alert(errorMsg);
    throw errorMsg;
  });
}

function urlParameters2map(urlParameters) {
  if (urlParameters.startsWith('?')) {
    urlParameters = urlParameters.substring(1);
  }
  const parameterPairs = urlParameters.split('&');
  const map = {};
  parameterPairs.forEach(parameterPair => {
    const keyValuePair = parameterPair.split('=');
    const key = encodeURIComponent(keyValuePair[0]);
    const value = encodeURIComponent(keyValuePair[1]);
    map[key] = value;
  });
  return map;
}

function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  return formattedDate;
}
