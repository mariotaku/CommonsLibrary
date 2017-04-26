#!/usr/bin/env python3

import json
import os
import urllib.request

filename = 'src/main/resources/assets/emojione/emojione.map'


def unicode_output_to_string(output):
    return ''.join(map(lambda s: chr(int(s, 16)), output.split("-")))


with urllib.request.urlopen(
        'https://raw.githubusercontent.com/Ranks/emojione/v3.0.1/emoji_strategy.json') as url:
    data = json.loads(url.read().decode())

if not os.path.exists(os.path.dirname(filename)):
    try:
        os.makedirs(os.path.dirname(filename))
    except OSError as exc:  # Guard against race condition
        if exc.errno != errno.EEXIST:
            raise

with open(filename, 'w') as f:
    f.write('%d\n' % len(data))
    for key in data:
        strategy = data[key]
        output = strategy['unicode_output']
        f.write('%s=%s\n' % (strategy['shortname'], unicode_output_to_string(output)))
