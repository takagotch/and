<?php
$str = mb_convert_encoding(file_get_contents('php://input'), 'SJIS', 'UTF-8');
print('����ɂ��́A'.$str.'����I');
