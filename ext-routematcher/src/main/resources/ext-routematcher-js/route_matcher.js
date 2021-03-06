/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

var utils = require('vertx-js/util/utils');
var HttpServerRequest = require('vertx-js/http_server_request');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JRouteMatcher = io.vertx.ext.routematcher.RouteMatcher;

/**

  @class
*/
var RouteMatcher = function(j_val) {

  var j_routeMatcher = j_val;
  var that = this;

  this.accept = function(request) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'object' && __args[0]._vertxgen) {
      j_routeMatcher.accept(request._jdel());
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP GET
  */
  this.get = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.get(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP PUT
  */
  this.put = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.put(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP POST
  */
  this.post = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.post(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP DELETE
  */
  this.delete = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.delete(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP OPTIONS
  */
  this.options = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.options(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP HEAD
  */
  this.head = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.head(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP TRACE
  */
  this.trace = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.trace(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP CONNECT
  */
  this.connect = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.connect(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP PATCH
  */
  this.patch = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.patch(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for all HTTP methods
  */
  this.all = function(pattern, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.all(pattern, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP GET
  */
  this.getWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.getWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP PUT
  */
  this.putWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.putWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP POST
  */
  this.postWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.postWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP DELETE
  */
  this.deleteWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.deleteWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP OPTIONS
  */
  this.optionsWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.optionsWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP HEAD
  */
  this.headWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.headWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP TRACE
  */
  this.traceWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.traceWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP CONNECT
  */
  this.connectWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.connectWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for a matching HTTP PATCH
  */
  this.patchWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.patchWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called for all HTTP methods
  */
  this.allWithRegEx = function(regex, handler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_routeMatcher.allWithRegEx(regex, function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  /*
   Specify a handler that will be called when no other handlers match.
   If this handler is not specified default behaviour is to return a 404
  
  */
  this.noMatch = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_routeMatcher.noMatch(function(jVal) {
      handler(new HttpServerRequest(jVal));
    });
      return that;
    } else utils.invalidArgs();
  };

  this._vertxgen = true;

  // Get a reference to the underlying Java delegate
  this._jdel = function() {
    return j_routeMatcher;
  }

};

RouteMatcher.routeMatcher = function() {
  var __args = arguments;
  if (__args.length === 0) {
    return new RouteMatcher(JRouteMatcher.routeMatcher());
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = RouteMatcher;