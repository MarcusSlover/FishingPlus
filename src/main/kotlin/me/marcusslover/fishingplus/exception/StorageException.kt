/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.exception

/**
 * Thrown when there's something wrong during the phase
 * of initializing a storage.
 */
class StorageException(val string: String) : Exception(string) {
}