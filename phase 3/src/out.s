.text
.globl main
methodname1A1:
	sw $a0, Adr6
	la $v0, Adr6
	jr $ra
	li $v0, 0
	jr $ra
methodname2A1:
	sw $a0, Adr8
	li $v0 , 1
	lw $a0 , Adr8
	syscall
	li $v0 , 4
	la $a0 , whiteSpace
	syscall
	li $v0, 0
	jr $ra
ErDZ:
	li $v0 , 4
	la $a0 , divideZero
	syscall
	li $v0, 10
	syscall
errorArrBound:
	li $v0 , 4
	la $a0 , outOfBound
	syscall
	li $v0, 10
	syscall
main:
	la $t0 , stringsSpace+16
	sw $t0 ,Adr16
	la $t0 , stringsSpace+0
	sw $t0 ,Adr15
	li $v0 , 8
	lw $a0, Adr16
	li $a1 , 16
	syscall
	lw $t1, Adr16
	sw $t1, Adr15
	li $v0 , 4
	lw $a0 , Adr15
	syscall
	li $v0 , 4
	la $a0 , whiteSpace
	syscall
	li $t1, 0
	sw $t1, Adr17
	lw $t1, Adr17
	sw $t1, Adr13
	li $t1, 1
	sw $t1, Adr18
	li $t1, 2
	sw $t1, Adr19
	li $t1, 4
	sw $t1, Adr20
	la $t0, Adr19
	la $t1, Adr20
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	mul $t4, $t2, $t3
	sw $t4, Adr21
	la $t0, Adr18
	la $t1, Adr21
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	add $t4, $t2, $t3
	sw $t4, Adr22
	lw $t1, Adr22
	sw $t1, Adr10
jps0:
	li $t1, 5
	sw $t1, Adr23
	la $t0, Adr13
	la $t1, Adr23
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	slt $t4, $t2, $t3
	sb $t4, Adr24
	li $t0 , 0
	lb $t0 , Adr24
	beqz $t0 , jps1
	lw $a0 ,Adr10
	addi $sp, $sp, -24
	sw $t0, 20($sp)
	sw $t1, 16($sp)
	sw $t2, 12($sp)
	sw $t3, 8($sp)
	sw $t4, 4($sp)
	sw $ra, 0($sp)
	jal methodname1A1
	lw $t0, 20($sp)
	lw $t1, 16($sp)
	lw $t2, 12($sp)
	lw $t3, 8($sp)
	lw $t4, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 24
	lw $t0, 0($v0)
	sw $t0, Adr25
	la $t0, Adr10
	la $t1, Adr25
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	add $t4, $t2, $t3
	sw $t4, Adr26
	li $v0 , 1
	lw $a0 , Adr26
	syscall
	li $v0 , 4
	la $a0 , whiteSpace
	syscall
	la $t0, Adr13
	lw $t1, 0($t0)
	addi $t1, $t1, 1
	sw $t1, Adr13
	b jps0
jps1:
	li $t1, 0
	sw $t1, Adr27
	lw $t1, Adr27
	sw $t1, Adr13
jps2:
	li $t1, 5
	sw $t1, Adr28
	la $t0, Adr13
	la $t1, Adr28
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	slt $t4, $t2, $t3
	sb $t4, Adr29
	li $t0 , 0
	lb $t0 , Adr29
	beqz $t0 , jps3
	la $t0, Adr13
	lw $t1, 0($t0)
	li $t1, 1
	sw $t1, Adr30
	la $t0, Adr10
	la $t1, Adr30
	lw $t2, 0($t0)
	lw $t3, 0($t1)
	add $t4, $t2, $t3
	sw $t4, Adr31
	lw $a0 ,Adr31
	addi $sp, $sp, -24
	sw $t0, 20($sp)
	sw $t1, 16($sp)
	sw $t2, 12($sp)
	sw $t3, 8($sp)
	sw $t4, 4($sp)
	sw $ra, 0($sp)
	jal methodname1A1
	lw $t0, 20($sp)
	lw $t1, 16($sp)
	lw $t2, 12($sp)
	lw $t3, 8($sp)
	lw $t4, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 24
	lw $t0, 0($v0)
	sw $t0, Adr32
	lw $t1, Adr32
	sw $t1, Adr14
	lw  $a0 ,Adr14
	addi $sp, $sp, -24
	sw $t0, 20($sp)
	sw $t1, 16($sp)
	sw $t2, 12($sp)
	sw $t3, 8($sp)
	sw $t4, 4($sp)
	sw $ra, 0($sp)
	jal methodname2A1
	lw $t0, 20($sp)
	lw $t1, 16($sp)
	lw $t2, 12($sp)
	lw $t3, 8($sp)
	lw $t4, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 24
	la $t0, Adr13
	lw $t1, 0($t0)
	addi $t1, $t1, 1
	sw $t1, Adr13
	b jps2
jps3:
	la $v0, Adr10
	jr $ra
	li $v0, 10
	syscall
.data
	stringsSpace: .space 50000
	arraysSpace: .space 50000
	whiteSpace: .asciiz " "
	divideZero: .asciiz "can not divide by zero!"
	outOfBound: .asciiz "the arrya index is out of bound!"
	Adr10: .word 0
	Adr6: .word 0
	Adr8: .word 0
	Adr13: .word 0
	Adr14: .word 0
	Adr15: .word 0
	Adr16: .word 0
	Adr17: .word 0
	Adr18: .word 0
	Adr19: .word 0
	Adr20: .word 0
	Adr21: .word 0
	Adr22: .word 0
	Adr23: .word 0
	Adr24: .space 1
	Adr27: .word 0
	Adr28: .word 0
	Adr29: .space 1
	Adr25: .word 0
	Adr26: .word 0
	Adr30: .word 0
	Adr31: .word 0
	Adr32: .word 0
