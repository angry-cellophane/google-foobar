#!/usr/bin/env python
# -*- coding: utf-8 -*-

import base64

MESSAGE = '''
EEYJFAgME10SCVMUGRcMEx8AH0haDkZNHEJVVQoGDwRMT0wORksAWlxVBgQeRkdPUUsHSBxcTUNM
QUBBTAYYTRNLF0dbXA5GVkFMDhVGCEsFS1RVBRVdQVFPUVsPQhxNUlUPRlZBTB0XTANHB10eEFFB
XRIKCRMJTQ5USFZfTEFAQUwYH0BACQ4=
'''

KEY = '<<REPLACEME>>' # your username in foobar console. type whoami to find it.

result = []
for i, c in enumerate(base64.b64decode(MESSAGE)):
    result.append(chr(ord(c) ^ ord(KEY[i % len(KEY)])))

print ''.join(result)
